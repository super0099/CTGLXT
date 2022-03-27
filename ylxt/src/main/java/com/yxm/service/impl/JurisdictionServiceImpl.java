package com.yxm.service.impl;

import com.yxm.dao.JurisdictionDao;
import com.yxm.dao.MenuDao;
import com.yxm.po.SysAuthorize;
import com.yxm.po.SysMenu;
import com.yxm.po.SysPosition;
import com.yxm.service.JurisdictionService;
import com.yxm.util.JsonMsg;
import com.yxm.vo.LayuiTableData;
import com.yxm.vo.LayuiTreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class JurisdictionServiceImpl implements JurisdictionService {
    @Autowired
    private JurisdictionDao jurisdictionDao;
    @Autowired
    private MenuDao menuDao;
    @Override
    public LayuiTableData<SysPosition> selectPageList(Integer page, Integer limit, String positionName) {
        Integer count = this.jurisdictionDao.selectCount();
        List<SysPosition> data = this.jurisdictionDao.selectPageList(page,limit,positionName);
        return new LayuiTableData<>(count,data);
    }

    @Override
    public SysPosition selectById(Integer positionId) {
        return this.jurisdictionDao.selectById(positionId);
    }

    @Override
    public boolean insert(SysPosition sysPosition) {
        return this.jurisdictionDao.insert(sysPosition)>0;
    }

    @Override
    public boolean update(SysPosition sysPosition) {
        return this.jurisdictionDao.update(sysPosition)>0;
    }

    @Override
    public JsonMsg deleteById(Integer positionId) {
        JsonMsg jsonMsg = new JsonMsg();
        //判断该职位是否还有人在使用
        Integer count = this.jurisdictionDao.selectPositionUse(positionId);
        if(count==0){
            //删除相关的权限设置
            this.jurisdictionDao.deleteAuthorize(positionId);
            boolean isOk = this.jurisdictionDao.deleteById(positionId)>0;
            if (isOk){
                jsonMsg.setMsg("删除成功");
                jsonMsg.setState(true);
            }else {
                throw new RuntimeException("删除异常");
            }
        }else {
            jsonMsg.setMsg("该职位正在使用中");
        }
        return jsonMsg;
    }

    @Override
    public JsonMsg authorize(int roleId, List<Integer> selectMenuIdList) {
        JsonMsg jsonMsg = new JsonMsg();
        //根据 角色id 查询出已经授权的的权限(菜单)id
        List<Integer> oldAuth = this.jurisdictionDao.selectMenuIdByPositionId(roleId);
        // 找出新增的权限 在新的list中，不在旧的list中的
        List<Integer> addIdList = new ArrayList<>();
        for (Integer id : selectMenuIdList) {
            if (!oldAuth.contains(id)) {
                addIdList.add(id);
            }
        }
        // 找出 移除的权限 在旧的list中 ，不在新的list中的
        List<Integer> removeList = new ArrayList<>();
        for (Integer id : oldAuth) {
            if (!selectMenuIdList.contains(id)) {
                removeList.add(id);
            }
        }

        //数据处理
        int count=0;
        SysAuthorize sysMenuAuthorize=null;
        //遍历添加
        for(Integer addMenuId:addIdList){
            sysMenuAuthorize=new SysAuthorize();
            sysMenuAuthorize.setPositionId(roleId);
            sysMenuAuthorize.setMenuId(addMenuId);
            count+=this.jurisdictionDao.inserts(sysMenuAuthorize);
        }
        if (count!=addIdList.size()){
            throw new RuntimeException("遍历添加 失败，总添加条数："+addIdList.size()+"；成功："+count);
        }
        //遍历移除
        count=0;
        for(Integer removeMenuId:removeList){
            count+=this.jurisdictionDao.deleteByIds(roleId,removeMenuId);
        }
        if (count!=removeList.size()){
            throw new RuntimeException("遍历移除 失败，总移除条数："+removeList.size()+"；成功："+count);
        }
        jsonMsg.setState(true);
        jsonMsg.setMsg("授权成功");
        return jsonMsg;
    }

    @Override
    public List<LayuiTreeVo> selectMenuForLayuiTree(Integer positionId) {
        // 所有的菜单
        List<SysMenu> menuList = this.menuDao.selectMenuAll();
        //查询出当前角色已经授权的菜单的id list
        List<Integer> authMenuIds = this.jurisdictionDao.selectMenuIdByPositionId(positionId);
        return dealMenuForLayuiTree(menuList,authMenuIds,0);
    }
    /**
     * 处理菜单 for layui Tree
     * @param listSource 所有菜单list
     * @param authMenuIds 该角色已经授权的菜单id list
     * @param pid 父id
     * @return
     */
    private List<LayuiTreeVo> dealMenuForLayuiTree(List<SysMenu> listSource, List<Integer> authMenuIds, int pid) {
        List<LayuiTreeVo> rList = new ArrayList<>();
        LayuiTreeVo layuiTreeVo=null;
        for (SysMenu menu : listSource) {
            if (menu.getParentId()==pid){
                layuiTreeVo=new LayuiTreeVo();
                layuiTreeVo.setId(menu.getId());
                layuiTreeVo.setTitle(menu.getMenuName());
                layuiTreeVo.setSpread(false);//默认为折叠状态

                List<LayuiTreeVo> listChildren=dealMenuForLayuiTree(listSource,authMenuIds,menu.getId());
                //layui tree 如果勾选父节点 layui Tree默认会勾选所有的子节点，哪怕子节点未勾选
                if (listChildren.size()>0){
                    //有子节点的不能checked
                    layuiTreeVo.setChildren(listChildren);
                }else {
                    layuiTreeVo.setChildren(null);
                    //么有子节点的 checked
                    //判断该节点的id 是否在 已经勾选菜单id list中，如在，就勾选
                    if (authMenuIds.contains(menu.getId())){
                        layuiTreeVo.setChecked(true);//勾选
                    }
                }
                rList.add(layuiTreeVo);
            }
        }
        return rList;
    }
}

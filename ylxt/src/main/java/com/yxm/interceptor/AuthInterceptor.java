package com.yxm.interceptor;

import com.yxm.dao.MenuDao;
import com.yxm.po.SysUser;
import com.yxm.service.MenuService;
import com.yxm.util.ProjectParameter;
import com.yxm.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*拦截器*/
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private MenuDao menuDao;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            System.out.println("-------------------------拦截器-----------------------------");
            System.out.println("拦截器获取请求路径: "+request.getRequestURI());
            System.out.println("拦截器获取到项目的根: "+request.getContextPath());

            //获取访问的url
            String requestURI = request.getRequestURI();
            //将项目的根从url中剔除
            String strUrl = requestURI.replace(request.getContextPath(),"");


            //排除不必要的拦截,比如登录页面,主页面,静态资源等
            //将""(首页),"/"(首页),"/home"(主页)根据自己的项目名称而定,"/login"(登录)根据自己的项目名称而定,"/static(静态资源)"
            if(strUrl.equals("")||strUrl.equals("/")||strUrl.startsWith("/home")||strUrl.startsWith("/login")||strUrl.startsWith("/static")){
                return true;
            }else {
                //获取当前登录用户的session
                HttpSession session = request.getSession();
                SysUser user = (SysUser) session.getAttribute(ProjectParameter.SESSION_USER);
                //判断session是否为空
                if(user==null){
                    response.sendRedirect(request.getContextPath());
                    return false;
                }else {
                    //获取被调用方法并且该方法存在@RequestMapping注解
                    RequestMapping rmp = ((HandlerMethod) handler).getMethodAnnotation(RequestMapping.class);
                    System.out.println("获取被调用方法并且该方法存在@RequestMapping注解: "+rmp+";");
                    //获取到被@RequestMapping注解的方法的value值
                    String method =getUrlByRequestMapping(rmp);
                    if(isOk(method)){
                        boolean hasAuth = this.menuDao.count(user.getPositionId(),strUrl)>0;
                        if(hasAuth){
                            return true;
                        }else {
                            //重定向到无权限页面
                            response.sendRedirect(request.getContextPath() + "/home/noAuth");
                        }
                    }else {
                        return true;
                    }
                }
            }
        }
        System.out.println("-------------------------=====-----------------------------");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }


    /**
     * 获取被@RequestMapping注解的方法中的value值,然后根据value的值可以得出该方法是属于什么方法
     * @param requestMapping
     * @return
     */
    private String getUrlByRequestMapping(RequestMapping requestMapping) {
        if(requestMapping==null){
            return null;
        }
        String[] controller = requestMapping.value();
        if(controller!=null||controller.length>0){
            if(Tools.isNotNull(controller[0])){
                return controller[0].replace("/","");
            }
        }
        return null;
    }

    private boolean isOk(String method){
        if (method.equals("appointment")||method.equals("check")||method.equals("renew")||method.equals("unsubscribe")
                ||method.equals("expire")||method.equals("change")||method.equals("guaranteeChange")||method.equals("inform")||method.equals("particular")
                ||method.equals("consume")||method.equals("await")||method.equals("advance")||method.equals("month")||method.equals("unsubscribe")
                ||method.equals("standard")||method.equals("arrearage")||method.equals("income")||method.equals("warn")||method.equals("location")
                ||method.equals("out")||method.equals("reservation")||method.equals("management")||method.equals("menu")||method.equals("maintain")
                ||method.equals("invoice")||method.equals("jurisdiction")||method.equals("issue")||method.equals("account")){
            return true;
        }else {
            return false;
        }
    }
}

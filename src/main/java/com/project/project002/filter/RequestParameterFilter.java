package com.project.project002.filter;

import com.alibaba.fastjson.JSONObject;
import com.project.project002.util.Status;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class RequestParameterFilter extends OncePerRequestFilter {



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


    	HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("Access-Control-Allow-Credentials", "true");
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT,OPTIONS");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN,Token_,userip ");
        if (((HttpServletRequest) request).getMethod().equals("OPTIONS")) {
            PrintWriter writer = null;
            try {
                writer= response.getWriter();
                writer.print("ok");
                filterChain.doFilter(request, response);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ServletException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }


        List<String> urls = new ArrayList<String>();
        urls.add("/swagger-ui.html");
        urls.add("/swagger-resources/configuration/ui");
        urls.add("/webjars/springfox-swagger-ui");
        urls.add("/swagger-resources");
        urls.add("/v2/api-docs");
        urls.add("/swagger-resources/configuration/security");
        urls.add("/webjars/springfox-swagger-ui/images/favicon-16x16.png");
        urls.add("/webjars/springfox-swagger-ui/images/favicon-32x32.png");

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (httpServletRequest.getMethod().equals("OPTIONS") ||  checkWhiteList(urls,httpServletRequest.getRequestURI())) {
        	filterChain.doFilter(request, response);
        	return;
        }

        String token = request.getHeader("Token_");
        if( null == token || "".equals(token)){
            JSONObject json = new JSONObject();
            json.put("code" , Status.ER_TOKEN );
            json.put("message" ,Status.ER_TOKEN_MSG );
            json.put("data" , Status.ER_TOKEN_MSG );
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.append(json.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }else{
            if (false) {
                JSONObject json = new JSONObject();
                json.put("code" , Status.ER_TOKEN );
                json.put("message" ,Status.ER_TOKEN_MSG );
                json.put("data" , Status.ER_TOKEN_MSG );
                responseOutWithJson(request, response, json.toString() );
            }else{
                MyResponseWrapper wrapper = new MyResponseWrapper(response);
                filterChain.doFilter(request, wrapper);
                byte[] bytes = wrapper.getBytes();
                String back = new String(bytes);
                String result = responseOutWithJson(request, response, back);
                logger.info("拦截的返回结果是====" + result );
            }
        }

    }
	/**
	 * 将通配符表达式转化为正则表达式
	 *
	 * @param path
	 * @return
	 */
	private String getRegPath(String path) {
		char[] chars = path.toCharArray();
		int len = chars.length;
		StringBuilder sb = new StringBuilder();
		boolean preX = false;
		for (int i = 0; i < len; i++) {
			if (chars[i] == '*') {//遇到*字符
				if (preX) {//如果是第二次遇到*，则将**替换成.*
					sb.append(".*");
					preX = false;
				} else if (i + 1 == len) {//如果是遇到单星，且单星是最后一个字符，则直接将*转成[^/]*
					sb.append("[^/]*");
				} else {//否则单星后面还有字符，则不做任何动作，下一把再做动作
					preX = true;
					continue;
				}
			} else {//遇到非*字符
				if (preX) {//如果上一把是*，则先把上一把的*对应的[^/]*添进来
					sb.append("[^/]*");
					preX = false;
				}
				if (chars[i] == '?') {//接着判断当前字符是不是?，是的话替换成.
					sb.append('.');
				} else {//不是?的话，则就是普通字符，直接添进来
					sb.append(chars[i]);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 通配符模式
	 *
	 * @param excludePath - 不过滤地址
	 * @param reqUrl      - 请求地址
	 * @return
	 */
	private boolean filterUrls(String excludePath, String reqUrl) {
		String regPath = getRegPath(excludePath);
		return Pattern.compile(regPath).matcher(reqUrl).matches();
	}

	/**
	 * 检验是否在非过滤地址
	 *
	 * @param excludeUrls
	 * @param reqUrl
	 * @return
	 */
	private boolean checkWhiteList(List<String> excludeUrls, String reqUrl) {
		for (String url : excludeUrls) {
			if (filterUrls(url, reqUrl)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取请求参数名和值
	 *
	 * @param request
	 * @return
	 */
	public String getInputParams(HttpServletRequest request) {
		String params = "";
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					params += "[" + paramName + "]=" + paramValue + ",";
				}
			}
		}
		return params;
	}

        protected String responseOutWithJson (HttpServletRequest request, HttpServletResponse response, String back){
            // 将实体对象转换为JSON Object转换
            JSONObject jsonResult = new JSONObject();
            if (null != back && back.startsWith("{")) {
                JSONObject json = JSONObject.parseObject(back);
                if ( json.containsKey("code") && !json.getString("code").equals(Status.ERR) ) {
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json; charset=utf-8");
                    PrintWriter out = null;
                    try {
                        out = response.getWriter();
                        out.append(back);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (out != null) {
                            out.close();
                        }
                    }
                    return "成功";
                } else {
                    jsonResult.put("code", Status.ERR);
                    jsonResult.put("message", Status.ERR_MSG);
                    jsonResult.put("data", "");
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json; charset=utf-8");
                    PrintWriter out = null;
                    try {
                        out = response.getWriter();
                        out.append(jsonResult.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (out != null) {
                            out.close();
                        }
                    }
                    return "失败";
                }
            } else {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = null;
                try {
                    out = response.getWriter();
                    out.append(back);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }
                return "成功";
            }
        }

    }


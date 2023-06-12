package com.example.javaadv_task_5.util.exception;

import org.springframework.web.context.request.WebRequest;

public class RequestDescriptionParser {
  private String resourceURN;
  private String clientIP;
  private String sessionId;
  private String userName;

  public RequestDescriptionParser(WebRequest request) {
    String strRequestDescription = request.getDescription(true);
    String[] requestProperties = strRequestDescription.split(";");
    for (String requestProperty : requestProperties) {
      String[] requestPropertyKeyValue = requestProperty.split("=");
      if ("uri".equals(requestPropertyKeyValue[0])) {
        resourceURN = requestPropertyKeyValue[1];
      }
      if ("client".equals(requestPropertyKeyValue[0])) {
        clientIP = requestPropertyKeyValue[1];
      }
      if ("session".equals(requestPropertyKeyValue[0])) {
        String sId = requestPropertyKeyValue[1];
        sessionId = (sId != null && sId.length() > 0) ? sId : request.getSessionId();
      }
      if ("user".equals(requestPropertyKeyValue[0])) {
        String uName = requestPropertyKeyValue[1];
        userName = (uName != null && uName.length() > 0) ? uName : request.getRemoteUser();
      }
    }
  }

  public String getResourceURN() {
    return resourceURN;
  }

  public String getClientIP() {
    return clientIP;
  }

  public String getSessionId() {
    return sessionId;
  }

  public String getUserName() {
    return userName;
  }
}

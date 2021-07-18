# client-v5

This is a copy of the `client/` sub-project but using the 5.x line of Apache HttpComponents instead of 4.x.

## Description

The development of the 5.x line of HttpComponents was a years-long effort and its first production-ready release (5.0 GA)
was in 2020. While a lot of projects continue to use the 4.x line and will continue to for years, I am still enthusiastic
to learn the 5.x line. 

## Wish List

General clean-ups, changes and things I wish to implement for this project:

* For some reason the HTTP client is logging a an exception, although the request is still successful. See this log:
  > 22:55:36 [main] INFO org.apache.hc.client5.http.impl.classic.HttpRequestRetryExec - Recoverable I/O exception (java.net.SocketException) caught when processing request to {}->http://localhost:8070

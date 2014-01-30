# Sling LogBack Appender for Logentries.com

This bundle makes the LogBack appender for Logentries.com usable in a Sling/CQ/AEM environment.

It requires a current SNAPSHOT build of Sling's Commons Log version 4.0.0 to take advantage of some recent changes, detailed in [SLING-3344](https://issues.apache.org/jira/browse/SLING-3344).

After installation, a new Configuration is availble (pid is `com.adobe.acs.logging.logentries.impl.LogentriesLogger`) which needs to be configured with the token from Logentries.com.

This is very much a work in progress. Use at your own risk.

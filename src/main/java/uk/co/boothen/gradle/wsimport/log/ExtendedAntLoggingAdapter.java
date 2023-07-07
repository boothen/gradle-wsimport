package uk.co.boothen.gradle.wsimport.log;

import org.apache.tools.ant.BuildEvent;
import org.gradle.api.AntBuilder;
import org.gradle.api.internal.project.ant.AntLoggingAdapter;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.internal.logging.LogLevelMapping;

public class ExtendedAntLoggingAdapter extends AntLoggingAdapter {
    private final Logger logger = Logging.getLogger(ExtendedAntLoggingAdapter.class);
    @Override
    public void messageLogged(BuildEvent event) {
        StringBuffer message = new StringBuffer();
        if (event.getTask() != null) {
            String taskName = event.getTask().getTaskName();
            message.append("[").append(taskName).append("] ");
        }
        final String messageText = event.getMessage();
        message.append(messageText);

        LogLevel level = getLogLevelForMessagePriority(event.getPriority());

        if (event.getException() != null) {
            logger.log(level, message.toString(), event.getException());
        } else {
            logger.log(level, message.toString());
        }
    }

    private LogLevel getLogLevelForMessagePriority(int messagePriority) {
        LogLevel defaultLevel = LogLevelMapping.ANT_IVY_2_SLF4J.get(messagePriority);

        // Check to see if we should adjust the level based on a set lifecycle log level
        if (getLifecycleLogLevel() != null) {
            if (defaultLevel.ordinal() < LogLevel.LIFECYCLE.ordinal()
                && AntBuilder.AntMessagePriority.from(messagePriority).ordinal() >= getLifecycleLogLevel().ordinal()) {
                // we would normally log at a lower level than lifecycle, but the Ant message priority is actually higher
                // than (or equal to) the set lifecycle log level
                return LogLevel.LIFECYCLE;
            } else if (defaultLevel.ordinal() >= LogLevel.LIFECYCLE.ordinal()
                       && AntBuilder.AntMessagePriority.from(messagePriority).ordinal() < getLifecycleLogLevel().ordinal()) {
                // would normally log at a level higher than (or equal to) lifecycle, but the Ant message priority is
                // actually lower than the set lifecycle log level
                return LogLevel.INFO;
            }
        }

        return defaultLevel;
    }
}

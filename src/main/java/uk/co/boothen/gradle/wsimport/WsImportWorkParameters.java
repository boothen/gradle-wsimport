package uk.co.boothen.gradle.wsimport;

import org.gradle.api.provider.Property;
import org.gradle.workers.WorkParameters;

public interface WsImportWorkParameters extends WorkParameters {

    Property<WsImportConfiguration> getWsImportConfiguration();
}

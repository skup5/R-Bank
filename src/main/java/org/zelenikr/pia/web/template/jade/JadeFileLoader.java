package org.zelenikr.pia.web.template.jade;

import de.neuland.jade4j.template.TemplateLoader;
import org.apache.commons.io.FilenameUtils;

import java.io.*;

/**
 * Own implementation of jade4j.template.TemplateLoader.
 *
 * @author Roman Zelenik
 */
public class JadeFileLoader implements TemplateLoader {

    private String encoding = "UTF-8";
    private String basePath = "";

    @Override
    public long getLastModified(String name) {
        try {
            return getFile(name).lastModified();
        } catch (SecurityException ex) {
            return -1;
        }
    }

    @Override
    public Reader getReader(String name) throws IOException {
        return new InputStreamReader(getInputStream(name), encoding);
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    private File getFile(String name) {
        return new File(FilenameUtils.concat(basePath, name));
    }

    private InputStream getInputStream(String name) {
        return getClass().getClassLoader().getResourceAsStream(FilenameUtils.concat(basePath, name));
    }
}

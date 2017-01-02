package org.zelenikr.pia.web.template;

import de.neuland.jade4j.template.TemplateLoader;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletContext;
import java.io.*;

/**
 * @author Roman Zelenik
 */
public class JadeFileLoader implements TemplateLoader {

    private String encoding = "UTF-8";
    private String basePath = "";
    private ServletContext context;

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

    public void setContext(ServletContext context) {
        this.context = context;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    private File getFile(String name) {
        return new File(FilenameUtils.concat(basePath, name));
    }

    private InputStream getInputStream(String name) {
        return context.getResourceAsStream(FilenameUtils.concat(basePath, name));
    }
}

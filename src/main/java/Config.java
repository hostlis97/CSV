import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {
    protected boolean loadEenabled;
    protected String loadFileName;
    protected String loadFormat;
    protected List<String> loadList = new ArrayList<>();

    protected boolean saveEenabled;
    protected String saveFileName;
    protected String saveFormat;
    protected List<String> saveList = new ArrayList<>();

    protected boolean logEenabled;
    protected String logFileName;
    protected List<String> logList = new ArrayList<>();


    public Config(File xmlConfig) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(xmlConfig.getName()));
            loadList = setElement("load", doc);
            saveList = setElement("save", doc);
            logList = setElement("log", doc);
            this.loadEenabled = Boolean.parseBoolean(loadList.get(0));
            this.loadFileName = loadList.get(1);
            this.loadFormat = loadList.get(2);
            this.saveEenabled = Boolean.parseBoolean(saveList.get(0));
            this.saveFileName = saveList.get(1);
            this.saveFormat = saveList.get(2);
            this.logEenabled = Boolean.parseBoolean(logList.get(0));
            this.logFileName = logList.get(1);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected List setElement(String xmlElement, Document doc) {
        List<String> list = new ArrayList<>();
        NodeList nodeList = doc.getElementsByTagName(xmlElement);
        Node first = nodeList.item(0);
        String[] xmlElementChild = first.getTextContent().split("\n");
        for (int i = 0; i < xmlElementChild.length; i++) {
            list.add(xmlElementChild[i].trim());
        }
        list.removeAll(Arrays.asList("", null));
        return list;
    }

    public boolean isLoadEenabled() {
        return loadEenabled;
    }

    public String getLoadFileName() {
        return loadFileName;
    }

    public String getLoadFormat() {
        return loadFormat;
    }

    public boolean isSaveEenabled() {
        return saveEenabled;
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    public String getSaveFormat() {
        return saveFormat;
    }

    public boolean isLogEenabled() {
        return logEenabled;
    }

    public String getLogFileName() {
        return logFileName;
    }
}

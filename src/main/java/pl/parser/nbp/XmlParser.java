package pl.parser.nbp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class XmlParser {

    public static void main(String[] args) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse("sample-nbp.xml");

            // Create XPathFactory object
            XPathFactory xpathFactory = XPathFactory.newInstance();

            // Create XPath object
            XPath xpath = xpathFactory.newXPath();


            Float purchasePrice = getPurchasePrice(doc, xpath, Currency.USD);
            System.out.println("Purchase price " + purchasePrice +"\n");

            Float askPrice = getAskPrice(doc, xpath, Currency.USD);
            System.out.println("Ask price " + askPrice);



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static CurrencyPrice parse(InputStream is, Currency currency) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;

        builder = factory.newDocumentBuilder();
        doc = builder.parse(new InputSource(is));

        // Create XPathFactory object
        XPathFactory xpathFactory = XPathFactory.newInstance();

        // Create XPath object
        XPath xpath = xpathFactory.newXPath();

        CurrencyPrice currencyPrice = new CurrencyPrice();
        currencyPrice.setPurchasePrice( getPurchasePrice(doc, xpath, currency) );
        currencyPrice.setAskPrice(getAskPrice(doc, xpath, currency));

        return currencyPrice;
    }




    //TODO: refactor
    private static Float getPurchasePrice(Document doc, XPath xpath, Currency currency) {
        List<String> list = new ArrayList<String>();
        try {
            XPathExpression expr =
                    xpath.compile("/tabela_kursow/pozycja[kod_waluty='" + currency.toString() + "']/kurs_kupna/text()");
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++)
                list.add(nodes.item(i).getNodeValue());
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return Float.valueOf(list.get(0).replace(",", "."));
    }

    //TODO: refactor
    private static Float getAskPrice(Document doc, XPath xpath, Currency currency) {
        List<String> list = new ArrayList<String>();
        try {
            XPathExpression expr =
                    xpath.compile("/tabela_kursow/pozycja[kod_waluty='" + currency.toString() + "']/kurs_sprzedazy/text()");
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++)
                list.add(nodes.item(i).getNodeValue());
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return Float.valueOf(list.get(0).replace(",", "."));
    }


}
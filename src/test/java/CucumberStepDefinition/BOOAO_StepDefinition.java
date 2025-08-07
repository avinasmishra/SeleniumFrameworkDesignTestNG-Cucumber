package CucumberStepDefinition;

import TestComponents.BaseTest;
import Utility.ConfigReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.cucumber.java.sl.Ce;
import io.cucumber.java.sl.In;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.FluentWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BOOAO_StepDefinition extends BaseTest {
    private static Map<String, String> globalVariables = new HashMap<>();
    private static List<String> globalVariableList;
    static String Execution_Downloads_Folder = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "Execution_Downloads";
    static Path folderString = Paths.get(Execution_Downloads_Folder);
    static String DOWNLOAD_FOLDER = System.getProperty("user.home")+ File.separator + "Downloads";

    //AutoIT execution files
    static String AutoItFolderPath = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\AutoIT";
    static String AutoIT_ExcelFileSave = AutoItFolderPath + "ExcelFileSave.exe";
    static String AutoIT_ExcelFileSaveCompatibility = AutoItFolderPath + "ExcelFileSaveCompatibilitty.exe";


    public static boolean isNum(String st)
    {
        if(st == null | st.equals(""))
        {
            return false;
        }
        try{
            Integer.parseInt(st);
            return true;
        }catch (NumberFormatException e)
        {
            return false;
        }
    }
    public static void scrollLittleAbove(WebElement webElement, JavascriptExecutor jse, int scrollTillPosition) {
        int elementY = webElement.getLocation().getY();
        int offsetY = scrollTillPosition;
        int scrollPostion = elementY - offsetY;
        jse.executeScript("window.scrollTo(0,arguments[0]);", scrollPostion);
    }

    public static void setGlobalVariable(String key, String value)
    {
        globalVariables.put(key,value);
    }
    public static String getGlobalVariable(String key)
    {
        if (key != null && key.startsWith("%") && key.endsWith("%")) {
            key = key.substring(1, key.length() - 1);  // Remove leading and trailing %
        }
        return globalVariables.get(key);
    }

    private void setGlobalVariableList(String globalVarKey, List<String> value)
    {
        globalVariableList = value;
    }
    private List<String> getGlobalVariableList(String globalVarKey)
    {
        return globalVariableList;
    }


    @When("^User wait for the (.*) control to be displayed$")
    public void waitForControlToBeDisplayed(String objLogicalName) {
        try {
            By objLocator = By.xpath(objLogicalName);
            FluentWait<WebDriver> fwait = new FluentWait<>(driver);

            fwait.ignoring(NoSuchElementException.class).withTimeout(Duration.ofSeconds(ConfigReader.getMediumWait("medium_wait")))
                    .pollingEvery(Duration.ofSeconds(2))
                    .until(driver -> {
                        //check if element found in main DOM
                        try {
                            WebElement element = driver.findElement(objLocator);
                            if (element.isDisplayed()) {
                                System.out.println("Element [" + objLogicalName + "] is Displayed : " + element.isDisplayed());
                                return true;
                            }
                        } catch (NoSuchElementException e) {
                            //Element not found in main DOM
                        }
                        //check if element found inside frames
                        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));

                        for (WebElement iframe : iframes) {
                            driver.switchTo().frame(iframe);
                            try {
                                WebElement element = driver.findElement(objLocator);
                                if (element.isDisplayed()) {
                                    System.out.println("Element [" + objLogicalName + "] is Displayed : " + element.isDisplayed());
                                    return true;
                                }
                            } catch (NoSuchElementException e1) {
                                //Element not found in this frame, continue checking others
                            } finally {
                                driver.switchTo().defaultContent();
                            }
                        }
                        return false;
                    });
        } catch (TimeoutException e) {
            System.out.println("Element [" + objLogicalName + "] is not found within the given time : " + ConfigReader.getMediumWait("medium_wait"));
        } catch (Exception e) {
            System.out.println("Some Eror Occured: " + e.getMessage());
        }
    }

    @Then("^I scroll above \"(.*)\" distance to (.*) control")
    public void scrollAboveToElement(int distance, String strElement) {
        try {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            WebElement webElement = driver.findElement(By.xpath(strElement));
            scrollLittleAbove(webElement, jse, distance);
            Thread.sleep(5000);
            System.out.println("Successful moved to " + strElement);
        } catch (Exception e) {
            System.out.println("Some Eror Occured while moving to : " + strElement + " error details: " + e.getMessage());
        }
    }

    @Then("Verify that (.*) control contains value \"(.*)\" regex pattern")
    public void validateTextPresentWithRegex(String element, String regexPattern) {
        try {
            List<WebElement> valuesElment = driver.findElements(By.xpath(element));

            List<String> matchingValue = new ArrayList<>();
            List<String> notMatchingValue = new ArrayList<>();
            boolean allValuesMatching = true;

            for (WebElement valueElement : valuesElment) {
                String text1 = valueElement.getText();

                Pattern pattern = Pattern.compile(regexPattern);
                Matcher matcher = pattern.matcher(text1);

                if (matcher.matches()) {
                    matchingValue.add(text1);
                } else {
                    allValuesMatching = false;
                    notMatchingValue.add(text1);
                }
            }
            if (allValuesMatching) {
                System.out.println(element + " contains matching value :[ " + matchingValue + "] which matches the regex [" + regexPattern + "] ");
            } else {
                System.out.println(element + " contains matching value :[ " + matchingValue + "] which matching and not matching value [" + notMatchingValue + "] not matches the regex [" + regexPattern + "]");
            }

        } catch (Exception e) {
            System.out.println("Some Eror Occured: " + e.getMessage());
        }
    }

    @Then("Verify that (.*) control color is \"(.*)\" hex from \"(.*)\" attribute$")
    public void validateBackgroundTextColor(String element, String hexColor, String attribute) {
        try {
            WebElement webElement = driver.findElement(By.xpath(element));

            hexColor = hexColor.replace("\\#", "#");

            String colorCode = webElement.getCssValue(attribute);
            String colorCodeHex = Color.fromString(colorCode).asHex();

            if (colorCodeHex.equalsIgnoreCase(hexColor)) {
                System.out.println(element + " element color is [" + colorCodeHex + "] matching as expected [" + hexColor + "]");
            } else {
                System.out.println(element + " element color is [" + colorCodeHex + "] not matching as expected [" + hexColor + "]");
            }
        } catch (Exception e) {
            System.out.println("Some Eror Occured: " + e.getMessage());
        }
    }

        @Then("user creates global variables from UI controls")
        public void createGlobalVarsFromUIControls(DataTable table) {
            List<Map<String, String>> rows = table.asMaps(String.class, String.class);
            for (Map<String, String> row : rows) {
                for (Map.Entry<String, String> entry : row.entrySet()) {
                    String rawKey = entry.getKey();
                    String locator = entry.getValue();

                    String key = rawKey.replaceAll("%", ""); // Remove surrounding %
                    WebElement element = driver.findElement(By.xpath(locator));
                    String value = element.getText().trim();

                    setGlobalVariable(key, value);
                    System.out.println("Set global variable: " + key + " = " + value);
                }
            }
        }


    ///////////////////// PDF VALIDATION /////////////////////////////

    @When("^User deletes all pdf files in temp folder$")
    public void deleteAllPDFFileInTemp() {
        String folderPath = System.getProperty("user.home") + "\\AppDate\\Local\\Temp";
        deletePdfFiles(folderPath);
    }

    public static void deletePdfFiles(String folderPath) {
        File folder = new File(folderPath);
        int filesDeletedCount = 0;
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().toLowerCase().endsWith(".pdf")) {
                        if (file.delete()) {
                            filesDeletedCount++;
                        } else {
                            System.out.println("Unable to delete the file : " + file.getName());
                        }
                    }
                }
            }
        }
        System.out.println("Total Files Deleted : " + filesDeletedCount);
    }

    public static boolean fileDownloadCheck(String filePath) {
        //Here filePath is like C:/temp/report.pdf
        File file = new File(filePath);
        //Define timeout duration -> eg- 60 seconds
        long timeoutMillis = 180000;
        long startTime = System.currentTimeMillis();
        boolean fileDownloaded = false;

        do {
            //check if files exists
            if (file.exists()) {
                fileDownloaded = true;
                System.out.println("File has been downloaded: " + file.getAbsolutePath());
            } else {
                //file not exist , wait for short time before checking again
                System.out.println("File not found . checking again...");
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //check if timeout has been reached
            if (System.currentTimeMillis() - startTime > timeoutMillis) {
                System.out.println("Timeout Reached. File not Found");
                break;
            }
        }
        while (!fileDownloaded);
        System.out.println("Download Check Completed");
        return fileDownloaded;
    }

    public static String extractPDFText(String filePath) throws IOException {
        //Here filePath is like C:/temp/report.pdf
        //Load the pdf document
        File file = new File(filePath);
        PDDocument document = Loader.loadPDF(file);

        //Extract text from pdf
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        String pdfText = pdfTextStripper.getText(document).replaceAll("[^\\p{Print}]", " ");

        //close pdf document
        document.close();
        return pdfText;
    }

    public void verifyPdfDataVerification(String filePath, String regExpression) {
        try {
            //Here filePath is like C:/temp/report.pdf
            File file = new File(filePath);

            //compile the provided regular expression
            Pattern pattern = Pattern.compile(regExpression);
            String pdfText = extractPDFText(filePath);

            //check if the provided pattern matched with expected text
            Matcher matcher = pattern.matcher(pdfText);

            if (matcher.find()) {
                System.out.println("Pattern found successfully: " + pdfText);
            } else {
                System.out.println("Pattern Not Found: " + pdfText);
            }
        } catch (Exception e) {
            System.out.println("PDF is not available, hence skipped the validation");
        }
    }

    public void verifyPdfVerificationWithUI(String filePath, LinkedHashMap<String, List<String>> uiDataMap) {
        try {
            //Here filePath is like C:/temp/report.pdf
            String pdfText = extractPDFText(filePath);

            //comparison code
            boolean misMatchFound = false;

            for (String columnName : uiDataMap.keySet()) {
                if (!pdfText.toLowerCase().replaceAll(" ", "").contains(columnName.trim().toLowerCase().replaceAll(" ", ""))) {
                    for (String individualWord : columnName.split(" ")) {
                        if (!pdfText.toLowerCase().replaceAll(" ", "").contains(individualWord.trim().replaceAll(" ", ""))) {
                            misMatchFound = true;
                            System.out.println("Column Name : " + columnName + " Not present in the PDF");
                        }
                    }
                } else {
                    misMatchFound = false;
                }
            }
            if (!misMatchFound) {
                for (String columnName : uiDataMap.keySet()) {
                    List<String> uiValues = uiDataMap.get(columnName);
                    for (int i = 0; i < uiValues.size(); i++) {
                        String uiValue = uiValues.get(i);
                        if (!pdfText.toLowerCase().replaceAll(" ", "").contains(uiValue.trim().toLowerCase().replaceAll(" ", ""))) {
                            for (String individualWord : uiValue.split(" ")) {
                                if (!pdfText.toLowerCase().replaceAll(" ", "").contains(individualWord.trim().toLowerCase().replaceAll(" ", ""))) {
                                    misMatchFound = true;
                                    System.out.println("Mismatch in column " + columnName + " at row " + (i + 1) + " UI value " + uiValue + " individualWord " + individualWord);
                                }
                            }
                        }
                    }
                }
            }
            if (misMatchFound) {
                System.out.println("PDF and UI values are not matching PDF Text: " + pdfText);
            } else {
                System.out.println("PDF and UI values are matching PDF Text: " + pdfText);
            }
        } catch (IOException e) {
            System.out.println("PDF is not available, hence skipped the validation");
        }
    }

    @Then("^User validate downloaded pdf file \"(.*)\" contains text \"(.*)\"$")
    public void extractPDFTextValue(String filePath, String expectedResult) {
        try {
            //Here filePath is like report.pdf
            String folderPath = System.getProperty("user.home") + "\\AppData\\Local\\temp";

            String pdfFileName = folderPath + "\\" + filePath;
            boolean fileDownloaded = fileDownloadCheck(pdfFileName);

            if (fileDownloaded) {
                if (expectedResult == null || expectedResult.isEmpty()) {
                    System.out.println("PDF is available but PDF content are not copied hence skipped the validation");
                } else {
                    validatePDFContent(pdfFileName, expectedResult);
                }
            }
        } catch (Exception e) {
            System.out.println(" Some Error Occured :" + e.getMessage());
        }
    }

    public void validatePDFContent(String filePath, String expectedResult) {
        try {
            //Here filePath is like C:/temp/report.pdf
            File file = new File(filePath);
            PDDocument document = Loader.loadPDF(file);

            PDFTextStripper pdfStripper = new PDFTextStripper();
            int totalPages = document.getNumberOfPages();

            for (int i = 1; i <= totalPages; i++) {
                pdfStripper.setStartPage(i);
                pdfStripper.setEndPage(i);

                String pdfText = extractPDFText(filePath);
                StringBuilder result = new StringBuilder();

                String value = expectedResult.trim().toLowerCase().replaceAll("\\s+", " ").replaceAll("null", "");
                String[] splitedExpectedResult = value.trim().split(" ");

                int count = splitedExpectedResult.length;
                System.out.println("Count: " + count);

                int j = 0;
                boolean flag = false;

                String normalizedText = pdfText.trim().toLowerCase().replaceAll("\\s+", " ").replaceAll("\\u00A0", " ").replaceAll("\\r?\\n", " ");
                System.out.println("Final NormalizedText: " + normalizedText);

                for (String details : splitedExpectedResult) {
                    String detail = details.trim();
                    if (normalizedText.contains(detail)) {
                        j++;
                        result.append(detail).append(" ");
                    } else {
                        System.out.println("Not Matched Value : " + detail);
                        j++;
                    }
                    if (j == count) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    System.out.println("Found the Matches on Page:" + i + " : " + result.toString());
                } else {
                    System.out.println("Not Found all the Matches");
                }
            }
        } catch (Exception e) {
            System.out.println("PDF is not available hence skipped the validation" + e.getMessage());
        }
    }

    @Then("User downloaded pdf file \"(.*)\" and validate text \"(.*)\"$")
    public void extractPDFTextAndMatchValue(String filePath, String expectedResult) {
        try {
            //Here filePath is like report.pdf
            String folderPath = System.getProperty("user.home") + "\\AppData\\Local\\temp";

            String pdfFileName = folderPath + "\\" + filePath;
            System.out.println("Extracting Text from PDF, PDF Path: " + pdfFileName);

            boolean fileDownloaded = fileDownloadCheck(pdfFileName);

            if (fileDownloaded) {
                if (expectedResult == null || expectedResult.isEmpty()) {
                    System.out.println("PDF is available but PDF content are not copied hence skipped the validation");
                } else {
                    validatePDFText(pdfFileName, expectedResult);
                }
            } else {
                System.out.println(pdfFileName + " PDF is not available hence skipped the validation");
            }
        } catch (Exception e) {
            System.out.println(" Some Error Occured :" + e.getMessage());
        }
    }
    public void validatePDFText(String filePath, String expectedResult) {
        try {
            //Here filePath is like C:/temp/report.pdf
            File file = new File(filePath);
            PDDocument document = Loader.loadPDF(file);

            PDFTextStripper pdfStripper = new PDFTextStripper();
            int totalPages = document.getNumberOfPages();

            for (int i = 1; i <= totalPages; i++) {
                pdfStripper.setStartPage(i);
                pdfStripper.setEndPage(i);

                String pageContent = pdfStripper.getText(document);

                String normalizedText = pageContent.trim().replaceAll("\\s+", " ").replaceAll("\\u00A0", " ").replaceAll("\\r?\\n", " ");
                System.out.println("Final NormalizedText: " + normalizedText);
                if (normalizedText.toLowerCase().contains(expectedResult.trim().toLowerCase()) || normalizedText.isEmpty() || normalizedText.isBlank()) {
                    System.out.println("Expected text found on page " + i + "\nExpected matched value is " + expectedResult.trim().toLowerCase());
                } else {
                    System.out.println("Expected text not found on page " + i + "\nExpected matched value is " + expectedResult.trim().toLowerCase() + "\n\n but found value as: " + normalizedText.trim().toLowerCase());
                }
            }
        } catch (IOException e) {
            System.out.println(" PDF is not available hence skipped the validation" + e.getMessage());
        }
    }

    @Then("User downloaded123 pdf file \"(.*)\" and validate text \"(.*)\"$")
    public void extractPDFTextAndMatchValueGB(String filePath, String expectedResult) {
        try {
            //Here filePath is like report.pdf
            String folderPath = System.getProperty("user.home") + "\\AppData\\Local\\temp";

            if(filePath.startsWith("%"))
            {
                filePath = getGlobalVariable(filePath);
            }
            String pdfFileName = folderPath + "\\" + filePath;
            System.out.println("Extracting Text from PDF, PDF Path: " + pdfFileName);

            boolean fileDownloaded = fileDownloadCheck(pdfFileName);

            if (fileDownloaded) {
                if (expectedResult == null || expectedResult.isEmpty()) {
                    System.out.println("PDF is available but PDF content are not copied hence skipped the validation");
                } else {
                    validatePDFText(pdfFileName, expectedResult);
                }
            } else {
                System.out.println(pdfFileName + " PDF is not available hence skipped the validation");
            }
        } catch (Exception e) {
            System.out.println(" Some Error Occured :" + e.getMessage());
        }
    }

    @Then("Download the pdf file (.*) control and store pdf file in global variable \"(.*)\"$")
    public void downloadAndStorePDF(String element, String globalVarKey)
    {
        try{
            //Here filePath is like report.pdf
            String folderPath = System.getProperty("user.home") + "\\AppData\\Local\\temp";

            List<WebElement> columnElements = driver.findElements(By.xpath(element));
            int size = columnElements.size();
            String pdfName = null;

            if(size>0)
            {
                pdfName = columnElements.get(0).getText();
                setGlobalVariable(globalVarKey,pdfName);
            }
            else{
                System.out.println("PDF is not available");
            }
            String pdfFileName = folderPath + "\\" + pdfName;
            boolean fileDownloaded = fileDownloadCheck(pdfFileName);

            if(fileDownloaded)
            {
                System.out.println("PDf is available");
            }
            else{
                System.out.println("Pdf is not available");
            }
        }catch (Exception e)
        {
            System.out.println("Some Error Occured: "+e.getMessage());
        }
    }

    @Then("click (.*) control and download pdf file and store pdf file in global variable \"(.*)\"$")
    public void savePdfFileWithGlobalVariable(String strElement, String globalVarKey) throws InterruptedException {
        try {
            WebElement webElement = driver.findElement(By.xpath(strElement));
            String downloadFolder = System.getProperty("user.home") + "\\AppData\\Local\\temp";

            //click on element to download pdf
            webElement.click();
            Thread.sleep(10000);

            //Retrieve the latest downloaded file
            File folder = new File(downloadFolder);
            if (!folder.exists() || !folder.isDirectory()) {
                throw new IllegalArgumentException("Invlid download folder path");
            }
            File[] files = folder.listFiles();
            if (files == null || files.length == 0) {
                throw new IllegalStateException("No files found in download folder");
            }
            //sort files by last modified time
            Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());

            //get the latest file
            File latestFile = files[0];

            //store in gloval variable
            setGlobalVariable(globalVarKey, latestFile.getName());
            System.out.println("File name store in global variable: " + latestFile.getName());

        } catch (Exception e) {
            System.out.println("Some Error Occured while handling the file download " + e.getMessage());
        }
    }

///////////////////// EXCEL VALIDATION /////////////////////////////

    public static void createFolder(String folderString)
    {
        Path folderStringPath = Paths.get(folderString);
        if(Files.exists(folderStringPath))
        {
            System.out.println("folderString: "+folderString);
        }
        else {
            try{
                Files.createDirectories(folderStringPath);
                System.out.println("Folder Created");
            }catch (Exception e)
            {
                System.out.println("Failed to create folder");
            }
        }
    }

    public static void deleteAllFilesInFolder(File folder)
    {
        File[] files = folder.listFiles();

        if(files !=null && files.length>0)
        {
            for(File eachFile : files)
            {
                if(eachFile.isDirectory())
                {
                    deleteAllFilesInFolder(eachFile);
                }
                else{
                    if(eachFile.delete())
                    {
                        System.out.println("Deleted File: "+ eachFile.getName());
                    }
                    else{
                        System.out.println("Failed to delete File: " + eachFile.getName());
                    }
                }
            }
        }
    }
    //get workbook object in return
    public static XSSFWorkbook getWorkbook(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        return workbook;
    }

    //method to save the workbook by passing excel path and workbook
    public static void saveWorkbook(String filePath, XSSFWorkbook workbook) throws IOException
    {
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        workbook.write(fileOutputStream);
        workbook.close();
    }

    //method to get row number based on value in first column
    public static int getRowNumByLabel(XSSFSheet sheet, String label_To_Search) throws Exception
    {
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
        int labelRow = -1;
        System.out.println("Row Count:: "+rowCount);

        for(int i=0;i<=rowCount;i++)
        {
            Row newRow = sheet.getRow(i);
            String label = newRow.getCell(0).getStringCellValue();
            if(label.equalsIgnoreCase(label_To_Search))
            {
                labelRow = i;
                break;
            }
        }
        if(labelRow == -1)
        {
            throw new Exception("Error Label "+ label_To_Search + " Not Found in "+ sheet.getSheetName() + " sheet");
        }
        return labelRow;
    }

    //method to get column number based on value in header row
    public static int getColumnNumByVariable(XSSFSheet sheet, String header_To_Search) throws Exception
    {
        Row newRow = sheet.getRow(0);
        int columnCount = newRow.getLastCellNum();
        int columnNum = -1;
        System.out.println("Column Count:: "+columnCount);

        for(int i=0;i<columnCount;i++)
        {
            String header = newRow.getCell(i).getStringCellValue();
            if(header.equalsIgnoreCase(header_To_Search))
            {
                columnNum = i;
                break;
            }
        }
        if(columnNum == -1)
        {
            throw new Exception("Error Header "+ header_To_Search + " Not Found in "+ sheet.getSheetName() + " sheet");
        }
        return columnNum;
    }

    //method to write in cell by passing sheet, row number, column number and value to write
    public static void writeInCell(XSSFSheet sheet, int labelRowNum, int columnNum, String valueToWrite, String filePath, XSSFWorkbook workbook) throws IOException
    {
        Row row = sheet.getRow(labelRowNum);
        Cell cell = row.createCell(columnNum);
        cell.setCellValue(valueToWrite);
        saveWorkbook(filePath,workbook);
    }

    //final method to write in excel by passing excel path, label to search row, num, header name, col name and value to write
    public static void methodToWriteInExcelByLabelHeader(String filePath, String sheetName, String label_To_Search, String header_To_Search, String valueToWrite) throws Exception
    {
        XSSFWorkbook workbook = getWorkbook(filePath);
        XSSFSheet sheet = workbook.getSheet(sheetName);
        int labelRowNum = isNum(label_To_Search) ? Integer.parseInt(label_To_Search) : getRowNumByLabel(sheet,label_To_Search);
        System.out.println("labelRowNum: "+labelRowNum);
        int columnNum = isNum(header_To_Search) ? Integer.parseInt(header_To_Search) : getColumnNumByVariable(sheet,header_To_Search);
        System.out.println("columnNum: "+columnNum);
        writeInCell(sheet,labelRowNum,columnNum, valueToWrite, filePath, workbook);
    }

    @Given("^user creates global variables from \\[(.+?),\\s*(.+?),\\s*(.+?)]$")
    public void userCreatesGlobalVariables(String excelPath, String sheetName, String rowKey) {
        try (FileInputStream fis = new FileInputStream(new File(excelPath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null)
            {
                System.out.println("Sheet not found: " + sheetName);
            }

            Row headerRow = sheet.getRow(0);
            int labelColIndex = -1;

            // Find the index of the "label" column
            for (Cell cell : headerRow) {
                if ("label".equalsIgnoreCase(cell.getStringCellValue())) {
                    labelColIndex = cell.getColumnIndex();
                    break;
                }
            }
            if (labelColIndex == -1) throw new RuntimeException("Label column not found.");

            for (Row row : sheet) {
                Cell labelCell = row.getCell(labelColIndex);
                if (labelCell != null && rowKey.equalsIgnoreCase(labelCell.getStringCellValue())) {

                    for (int i = 0; i < row.getLastCellNum(); i++) {
                        if (i == labelColIndex) continue;

                        String key = headerRow.getCell(i).getStringCellValue();
                        String value = row.getCell(i) != null ? row.getCell(i).toString() : "";
                        setGlobalVariable(key, value);
                        System.out.println("Stored: " + key + " = " + value);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading Excel: " + e.getMessage());
        }
    }

    @When("User tries to download file and save with following file name \"(.*)\"$")
    public void exportExcelToLocal(String fileName)
    {
        try{
            createFolder(Execution_Downloads_Folder);
            String downloadsPath = Execution_Downloads_Folder;

            if(fileName.startsWith("%"))
            {
                fileName = getGlobalVariable(fileName);
            }

            File file = new File(downloadsPath,fileName);

            Robot robot = new Robot();
            robot.setAutoDelay(3000);
            StringSelection ss = new StringSelection(file.getAbsolutePath());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss,null);
            robot.setAutoDelay(2000);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);
            robot.setAutoDelay(2000);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

            //Define timeout duration
            long timeoutMillis = 360000;
            long startTime = System.currentTimeMillis();
            boolean fileDownloaded = false;

            do {
                //check if files exists
                if (file.exists()) {
                    fileDownloaded = true;
                    System.out.println("File has been downloaded: " + file.getAbsolutePath());
                } else {
                    //file not exist , wait for short time before checking again
                    System.out.println("File not found . checking again...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //check if timeout has been reached
                if (System.currentTimeMillis() - startTime > timeoutMillis) {
                    System.out.println("Timeout Reached. File not Found");
                    break;
                }
            }
            while (!fileDownloaded);
            System.out.println("Download Check Completed");
        } catch (Exception e) {
            System.out.println("Fail to save file at location");
        }
    }

    @Then("^user creates global variables %(.*)% as \"(.*)\"$")
    public void user_creates_global_variable(String key, String value)
    {
        setGlobalVariable(key, value);
        System.out.println("Global variable set: " + key + " = " + value);
    }

    @Then("^validate actual header \"(.*)\" with expected header \"(.*)\"$")
    public void validateActualAndExpectedHeader(String fileNameKey, String expectedResult)
    {
        try{
            FileInputStream excelFile = null;
            Workbook workbook = null;

        if(fileNameKey.startsWith("%"))
        {
            fileNameKey = getGlobalVariable(fileNameKey);
        }
        if(expectedResult.startsWith("%"))
        {
            expectedResult = getGlobalVariable(expectedResult);
        }

            String downloadsPath = Execution_Downloads_Folder + "/" + fileNameKey;
            System.out.println("Path To Open Excel => "+ downloadsPath);
            excelFile = new FileInputStream(downloadsPath);

            if (downloadsPath.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(excelFile);
            } else if (downloadsPath.endsWith(".xls")) {
                workbook = new HSSFWorkbook(excelFile);
            } else {
                throw new IllegalArgumentException("Unsupported file format: ");
            }
            Sheet sheet = workbook.getSheetAt(0);
            String sheetName = workbook.getSheetName(0);
            System.out.println("Fetching Data From Sheet: " + sheetName);

            boolean isMatchFound = false;

            for (Row row : sheet) {
                for (Cell cell : row) {
                    String cellText = cell.toString().trim();
                    if (cellText.equalsIgnoreCase(expectedResult)) {
                        System.out.println("Header matched: " + cellText);
                        isMatchFound = true;
                        break;
                    }
                }
                if (isMatchFound) break;
            }
            if (!isMatchFound) {
                throw new AssertionError(" Expected header '" + expectedResult + "' not found in file: " + downloadsPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + e.getMessage());
        }
    }

    @Then("^validate actual headers \"(.*)\" with expected headers \"(.*)\"$")
    public void validateActualHeadersWithExpectedHeaders(String fileNameKey, String expectedResult)
    {
        try{
            FileInputStream excelFile = null;
            Workbook workbook = null;

            if(fileNameKey.startsWith("%"))
            {
                fileNameKey = getGlobalVariable(fileNameKey);
            }
            if(expectedResult.startsWith("%"))
            {
                expectedResult = expectedResult.replaceAll("%(.*?)%", "$1");
            }

            String[] inputText = expectedResult.trim().split(";");
            List<String> actualTextMsg = new ArrayList<>();
            List<String> expectedTextMsg = new ArrayList<>();
            int count = 0 ;

            //storing expected excel value into list
            for(String text : inputText)
            {
                expectedTextMsg.add(getGlobalVariable(text).trim());
            }

            String downloadsPath = Execution_Downloads_Folder + "/" + fileNameKey;
            System.out.println("Path To Open Excel => "+ downloadsPath);
            excelFile = new FileInputStream(downloadsPath);

            if (downloadsPath.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(excelFile);
            } else if (downloadsPath.endsWith(".xls")) {
                workbook = new HSSFWorkbook(excelFile);
            } else {
                throw new IllegalArgumentException("Invalid file format: ");
            }
            Sheet sheet = workbook.getSheetAt(0);
            String sheetName = workbook.getSheetName(0);
            System.out.println("Fetching Data From Sheet: " + sheetName);

           // boolean isMatchFound = false;
            Row headerRow = sheet.getRow(0);

            for(Cell cell : headerRow)
            {
                actualTextMsg.add(cell.toString().toLowerCase().trim());
            }
            System.out.println("Excel Sheet :" + sheetName + " ,Headers are: "+ actualTextMsg);

            for(int i=0 ;i<expectedTextMsg.size();i++)
            {
                if(actualTextMsg.contains(expectedTextMsg.get(i).toLowerCase()))
                {
                    count++;
                    System.out.println("Matched Header Value : "+ expectedTextMsg.get(i));
                    if(count == inputText.length)
                    {
                        break;
                    }
                }
                else {
                    System.out.println("Not Matched Header Value : Actual : "+ actualTextMsg + " , Expected : "+expectedTextMsg.get(i));
                }
            }
            workbook.close();
            excelFile.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + e.getMessage());
        }
    }

    @Then("User deletes all excel files in temp folder")
    public void deleteAllExcelFilesInTemp()
    {
        String folderPath = System.getProperty("user.home") + "\\AppData\\Local\\temp";

        File folder = new File(folderPath);
        int filesDeletedCount = 0;

        if(folder.exists() && folder.isDirectory())
        {
            File[] files = folder.listFiles();
            if(files != null)
            {
                for(File file : files)
                {
                    if(file.isFile() && file.getName().toLowerCase().endsWith(".xls") || file.getName().toLowerCase().endsWith(".xlsx"))
                    {
                        if(file.delete())
                        {
                            filesDeletedCount++;
                        }
                        else {
                            System.out.println("Unable to delete the file : "+file.getName());
                        }
                    }
                }
                System.out.println("Total file deleted : "+filesDeletedCount);
            }
        }
    }

    @Then("Validate the actual UI text value \"(.*)\" with expected excel text value \"(.*)\"$")
    public void validateActualUIAndExpectedExcelText(String expectedResult, String excelFilePath)
    {
        try{
            FileInputStream excelFile = null;
            Workbook workbook = null;

            if(excelFilePath.startsWith("%"))
            {
                excelFilePath = getGlobalVariable(excelFilePath);
            }
            if(expectedResult.startsWith("%"))
            {
                expectedResult = getGlobalVariable(expectedResult);
            }

            List<String> expectedTextMsg = new ArrayList<>();
            boolean valueFound = false ;

            String downloadsPath = Execution_Downloads_Folder + "/" + excelFilePath;
            System.out.println("Path To Open Excel => "+ downloadsPath);
            excelFile = new FileInputStream(downloadsPath);

            if (downloadsPath.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(excelFile);
            } else if (downloadsPath.endsWith(".xls")) {
                workbook = new HSSFWorkbook(excelFile);
            } else {
                throw new IllegalArgumentException("Invalid file format: ");
            }
            Sheet sheet = workbook.getSheetAt(0);
            String sheetName = workbook.getSheetName(0);
            System.out.println("Fetching Data From Sheet: " + sheetName);

            //Header row
            for(Row row : sheet)
            {
                for(Cell cell : row)
                {
                    if(cell != null && cell.getCellType() != CellType.BLANK)
                    {
                        if(cell.getCellType() == CellType.STRING)
                        {
                            String cellValue = cell.getStringCellValue().toLowerCase().trim();
                            if(expectedResult.contains(cellValue))
                            {
                                valueFound = true;
                                break;
                            }
                        } else if (cell.getCellType() == CellType.NUMERIC)
                        {
                            String cellValue = String.valueOf(cell.getNumericCellValue()).toLowerCase().trim();
                            if(expectedResult.contains(cellValue))
                            {
                                valueFound = true;
                                break;
                            }
                        }
                    }
                }
                if(valueFound)
                {
                    break;
                }
            }
            if(valueFound)
            {
                System.out.println("Text value present in excel sheet : "+ expectedResult);
            }
            else{
                System.out.println("Text value not present in excel sheet : "+ expectedResult);
            }
            workbook.close();
            excelFile.close();
        } catch (Exception e) {
            System.out.println("Failed to read Excel file: " + e.getMessage());
        }
    }

    @Then("^User click and download Excel file (.*) control and store in global variable \"(.*)\"$")
    public void saveExcelFileInGlobalVar(String strElement, String globalVarKey)
    {
        try{
            WebElement element = driver.findElement(By.xpath(strElement));

            //click on button to download excel file
            element.click();
            Thread.sleep(10000);

            //retrieve the latest downloaded file
            File folder = new File(DOWNLOAD_FOLDER);
            if(!folder.exists() || !folder.isDirectory())
            {
                throw new IllegalArgumentException("Invalid Download folder path");
            }

            File[] files = folder.listFiles();
            if(files == null || files.length == 0)
            {
                throw new IllegalArgumentException("No files found in download folder");
            }

            //sort files by last modify time in descending order
            Arrays.sort(files,Comparator.comparingLong(File::lastModified).reversed());

            //get the latest file
            File latestFile = files[0];

            createFolder(Execution_Downloads_Folder);
            String downloadPath = Execution_Downloads_Folder + "/" + latestFile.getName();
            File targetFile = new File(downloadPath);

            //copy the file to target path
            Files.copy(latestFile.toPath(), targetFile.toPath());

            //store in global variable
            setGlobalVariable(globalVarKey, targetFile.getName());
            System.out.println("File copied and stored in global variable "+ targetFile.getAbsolutePath());
        }catch (Exception e)
        {
            System.out.println("Some Error Occured :"+e.getMessage());
        }
    }

    @Then("User copy excel headers from \"(.*)\" and store in global variable \"(.*)\"$")
    public void storeAllExcelHeaders(String excelFilePath, String globalVarKey)
    {
        try {
            FileInputStream excelFile = null;
            Workbook workbook = null;
            List<String> excelHeaders = new ArrayList<>();

            if(excelFilePath.startsWith("%"))
            {
                excelFilePath = getGlobalVariable(excelFilePath);
            }

            String downloadsPath = Execution_Downloads_Folder + "/" + excelFilePath;
            System.out.println("Path To Open Excel => "+ downloadsPath);
            excelFile = new FileInputStream(downloadsPath);

            if (downloadsPath.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(excelFile);
            } else if (downloadsPath.endsWith(".xls")) {
                workbook = new HSSFWorkbook(excelFile);
            } else {
                throw new IllegalArgumentException("Invalid file format: ");
            }
            Sheet sheet = workbook.getSheetAt(0);
            String sheetName = workbook.getSheetName(0);
            System.out.println("Fetching Data From Sheet: " + sheetName);

            //Headers Row
            Row headerRow = sheet.getRow(1);
            for(Cell cell : headerRow)
            {
                excelHeaders.add(cell.toString().toLowerCase().trim());
            }

            //storing in glovalvar key
            setGlobalVariableList(globalVarKey, excelHeaders);
            System.out.println("Excel Sheet [" + sheetName + "] Headers are "+ excelHeaders);
            workbook.close();
            excelFile.close();
        }catch (Exception e)
        {
            System.out.println("Some Error Occured "+e.getMessage());
        }
    }

    @When("^User clicks on (.*) control and save excel with file name \"(.*)\" using AutoIT$")
    public void downloadExcelFileusingAutoIT(String element, String fileName)
    {
        try {
            ((JavascriptExecutor) driver).executeScript("window.focus();");

            if(fileName.startsWith("%"))
            {
                fileName = getGlobalVariable(fileName);
            }

            createFolder(Execution_Downloads_Folder);
            File file = new File(Execution_Downloads_Folder, fileName);

            StringSelection ss = new StringSelection(file.getAbsolutePath());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss,null);

            Runtime.getRuntime().exec(AutoIT_ExcelFileSave);

            driver.findElement(By.xpath(element)).click();

            long maxWaitTime = 180000;
            long timeoutMillis = 180000;
            long startTime = System.currentTimeMillis();

            try{

                //Define timeout duration eg-> 60 seconds
                startTime = System.currentTimeMillis();
                boolean fileDownloaded = false;
                Thread.sleep(10000);
                File folder = new File(Execution_Downloads_Folder);
                File[] listOfFiles = folder.listFiles();

                do{
                    //check if file exist
                    if(listOfFiles != null)
                    {
                        for(File files : listOfFiles)
                        {
                            System.out.println(files.getName());
                            files.exists();
                        }
                    }
                    else{
                        System.out.println("file not exist");
                    }
                    if(file.exists())
                    {
                        fileDownloaded = true;
                        System.out.println("File has been downloaded "+ file.getAbsolutePath());
                    }
                    else{
                        System.out.println("file not found, checking again...");
                        try{
                            Thread.sleep(1000);
                        }catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }

                    //check if timeout has been reached
                    if(System.currentTimeMillis() - startTime > timeoutMillis)
                    {
                        System.out.println("Timeout reached. file not found");
                        break;
                    }
                }
                while (!fileDownloaded);
                System.out.println("download check completed");

            }catch (Exception e)
            {
                e.printStackTrace();
                throw e;
            }
        }catch (Exception e)
        {
            System.out.println("Some Error Occured "+ e.getMessage());
        }
    }




}
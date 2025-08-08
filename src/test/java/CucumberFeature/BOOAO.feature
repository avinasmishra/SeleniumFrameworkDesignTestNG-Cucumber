Feature: B00AO Feature Files Steps

  Scenario: Verify the B00AO product

    When User wait for the web_Element control to be displayed
    Then I scroll above "400" distance to webElement control
    Then Verify that webElement control contains value "My name is" regex pattern
    Then Verify that webElement control color is "#6688A4" hex from "background-color" attribute
    Then user creates global variables from UI controls
      |%Variable_Name%|
      |webElement|
    When User set date in From Date webElement1 control along with To Date webElement2 control and click on webElement3 control post verify records searchResult_List control displayed
    Then Verify multiple documents webElement control should not be upload at a time
    When User upload file for webElement control with filename as text_file1.txt without filetype


    ## PDF
    When User deletes all pdf files in temp folder
    Then User validate downloaded pdf file "c:user/temp/report.pdf" contains text "manager"
    Then User downloaded pdf file "c:user/temp/report.pdf" and validate text "manager"
    Then Download the pdf file webElement control and store pdf file in global variable "Var_pdfFileName"
    Then click webElement control and download pdf file and store pdf file in global variable "Var_pdfFileName"
    Then User downloaded123 pdf file "%Var_pdfFileName%" and validate text "manager"

    ## EXCEL
    Given user creates global variables from [src/test/resources/data/Input_data.xlsx, pdf_sheet, Headers_Names]
    Then user creates global variables %fileName% as "Reports_Excel.xlsx"
    When User tries to download file and save with following file name "%fileName%"
    Then validate actual header "%fileName%" with expected header "%ExcelText1%"
    Then validate actual headers "%fileName%" with expected headers "%ExcelText1%;%ExcelText2%;%ExcelText3%"
    Then User deletes all excel files in temp folder
    Then user creates global variables from UI controls
      |%Variable_Name%|
      |webElement|
    Then Validate the actual UI text value "%Variable_Name%" with expected excel text value "%fileName%"
    Then User click and download Excel file webElement control and store in global variable "fileName"
    Then User copy excel headers from "%filename%" and store in global variable "Var_VD_Headers"
    When User clicks on webElement_ExcelICon control and save excel with file name "%fileName%" using AutoIT





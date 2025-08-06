Feature: B00AO Steps

  Scenario: Verify the B00AO product

    When User wait for the web_Element control to be displayed
    Then I scroll above "400" distance to webElement control
    Then Verify that webElement control contains value "My name is" regex pattern
    Then Verify that webElement control color is "#6688A4" hex from "background-color" attribute

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




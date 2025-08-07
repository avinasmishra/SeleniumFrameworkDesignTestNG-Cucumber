WinActivate("File Download")
IF WinWaitActive("File Download" ,"",60) Then
    ControlFocus("File Download","", "Button1")
    ControlClick("File Download","", "Button1")
    Sleep(5000)
    WinActivate("[CLASS:XLMAIN]")
    IF WinWaitActive("Excel","","NetUIHWND") Then
        ControlFocus("Excel","","NetUIHWND")
        Sleep(5000)
        Send("{F12}")
        WinActivate("Save As")
        IF WinWaitActive("Save As","",10) Then
            Sleep(5000)
            ControlFocus("Save As","","Edit1")
            Send("^v")
            ControlFocus("Save As","","[CLASS:Button; INSTANCE:8]")
            ControlClick("Save As","","[CLASS:Button; INSTANCE:8]")
            Sleep(10000)
            WinActivate("[CLASS:XLMAIN]")
            WinClose("[CLASS:XLMAIN]")
        EndIf
    EndIf
EndIf

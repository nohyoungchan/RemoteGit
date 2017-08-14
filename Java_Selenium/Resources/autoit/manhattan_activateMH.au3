


While 1 ;use infinite loop since ExitLoop will get called
   Opt("WinTitleMatchMode", 2)
   If WinExists("ShoreTel Connect") Then
	  ;MsgBox(4096, "manhattan", "Relocating to " + $xLocation + " and " + $yLocation, 3)
	  Sleep(3000)
	 WinActivate("ShoreTel Connect")
	  Exit
   EndIf
	  MsgBox(4096, "manhattan", "manhattan client cannot find with ShoreTel Connect title", 3)
      Sleep(1000)

WEnd


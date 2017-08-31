


While 1 ;use infinite loop since ExitLoop will get called
   Opt("WinTitleMatchMode", 3)
   If WinExists("ShoreTel Connect") Then
	  ;MsgBox(4096, "manhattan", "Relocating to: something is not right1", 3)
	  Sleep(1000)
	  WinActivate("ShoreTel Connect")
	  Exit(1)
   EndIf
	  MsgBox(4096, "manhattan", "manhattan client cannot find with ShoreTel Connect title", 3)
      Sleep(1000)
	  Exit(0)

WEnd


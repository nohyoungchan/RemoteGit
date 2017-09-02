
;$CmdLine[0] is the name of the this script.
;To run this script: AutoIt3.exe manhattan_relocate.exe 30  30
Local $xLocation = $CmdLine[1]
Local $yLocation = $CmdLine[2]

;Local $xLocation = 10
;Local $yLocation = 50

While 1 ;use infinite loop since ExitLoop will get called
   Opt("WinTitleMatchMode", 2)
   If WinExists("ShoreTel Connect") Then
	  ;MsgBox(4096, "manhattan", "Relocating to " + $xLocation + " and " + $yLocation, 3)
	  Sleep(4000)
	  MsgBox(4096, "manhattan", "Relocating manhattan client to " & $xLocation & "/" & $yLocation, 3)
	  WinMove("ShoreTel Connect", "", $xLocation, $yLocation)
	  Exit
   EndIf
	  MsgBox(4096, "manhattan", "manhattan client is not started yet..wating", 3)
      Sleep(1000)

WEnd


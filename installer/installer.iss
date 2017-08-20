[Setup]
AppId={{A985BBBE-2FC7-4C27-AE9B-F32D5FA05915}
AppName=Xemime
AppVersion=1.0
;AppVerName=Xemime 1.0
AppPublisher=Kodai Matsumoto
AppPublisherURL=http://0918nobita.net/Xemime/
AppSupportURL=http://0918nobita.net/Xemime/
AppUpdatesURL=http://0918nobita.net/Xemime/
DefaultDirName={pf}\Xemime
DisableProgramGroupPage=yes
LicenseFile=..\LICENSE
OutputBaseFilename=Xemime_v1.0_setup
Compression=lzma
SolidCompression=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"
Name: "japanese"; MessagesFile: "compiler:Languages\Japanese.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
Source: "..\icon.ico"; DestDir: "{app}"; Flags: ignoreversion
Source: "launch.bat"; DestDir: "{app}"; Flags: ignoreversion
Source: "..\target\Xemime-1.0a3.jar"; DestDir: "{app}"; Flags: ignoreversion
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{commonprograms}\Xemime"; Comment: "Xemime Interpreter"; Filename: "{app}\launch.bat"; IconFilename: "{app}\icon.ico"
Name: "{commondesktop}\Xemime"; Comment: "Xemime Interpreter"; Filename: "{app}\launch.bat"; Tasks: desktopicon; IconFilename: "{app}\icon.ico"

[Run]
Filename: "{app}\launch.bat"; Description: "{cm:LaunchProgram,Xemime}"; Flags: shellexec postinstall skipifsilent

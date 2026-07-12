#!/bin/bash

chmod -R 777 .build 2>/dev/null
rm -rf .build 2>/dev/null

if [ -z "$GOPATH" ]; then
    GOPATH=$(go env GOPATH)
fi

# Install gomobile
if [ ! -f "$GOPATH/bin/gomobile-matsuri" ]; then
    git clone https://github.com/MatsuriDayo/gomobile.git
    pushd gomobile
	git checkout origin/master2

    # Patch getModuleVersions: write minimal go.mod when go list -m fails
    sed -i '/\/\/ Module information is not available at src\./{
N;N;s|// Module information is not available at src\.\n\t\treturn nil, nil\n\t}|_ = output\n\t\tf := \&modfile.File{}\n\t\tf.AddModuleStmt("gobind")\n\t\tprojectDir, _ := filepath.Abs(src)\n\t\tgoModData, _ := os.ReadFile(filepath.Join(projectDir, "go.mod"))\n\t\tif f2, _ := modfile.Parse("go.mod", goModData, nil); f2 != nil \&\& f2.Module != nil {\n\t\t\tf.AddReplace(f2.Module.Mod.Path, "", projectDir, "")\n\t\t}\n\t\tv, _ := ensureGoVersion()\n\t\tif v == "" {\n\t\t\tv = fmt.Sprintf("go1.%d", minimumGoMinorVersion)\n\t\t}\n\t\tf.AddGoStmt(strings.TrimPrefix(v, "go"))\n\t\treturn f, nil\n\t}|
}' cmd/gomobile/bind.go

    pushd cmd
    pushd gomobile
    go install -v
    popd
    pushd gobind
    go install -v
    popd
    popd
    rm -rf gomobile
    mv "$GOPATH/bin/gomobile" "$GOPATH/bin/gomobile-matsuri"
    mv "$GOPATH/bin/gobind" "$GOPATH/bin/gobind-matsuri"
fi

GOBIND=gobind-matsuri gomobile-matsuri init

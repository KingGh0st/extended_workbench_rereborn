import zipfile
p='build/moddev/artifacts/neoforge-21.1.217-sources.jar'
with zipfile.ZipFile(p) as z:
    for name in z.namelist():
        if 'AbstractSkeleton' in name:
            print(name)

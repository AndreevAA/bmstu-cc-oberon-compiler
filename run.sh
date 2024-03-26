cd oberon-compiler
chmod +x gradlew
./gradlew clean
./gradlew dependencies --write-locks
./gradlew build --refresh-dependencies
./gradlew build
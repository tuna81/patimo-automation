# 1. Adım: Doğrudan Debian Bookworm (Kararlı Sürüm) kullanıyoruz.
# Bu imaj hem M1 Mac hem Intel işlemcilerde sorunsuz çalışır.
FROM debian:bookworm

ARG DEBIAN_FRONTEND=noninteractive

# 2. Adım: Paketleri güncelliyoruz ve ihtiyacımız olan her şeyi kuruyoruz.
# - openjdk-17-jdk: Java 17
# - maven: Projeyi build etmek için
# - chromium & driver: Testler için
RUN apt-get update && apt-get install -y --no-install-recommends \
    openjdk-17-jdk \
    maven \
    chromium \
    chromium-driver \
    fonts-liberation \
    libnss3 \
    libatk-bridge2.0-0 \
    libdrm2 \
    libxkbcommon0 \
    libgtk-3-0 \
    libgbm1 \
    libasound2 \
    xdg-utils \
    ca-certificates \
    && rm -rf /var/lib/apt/lists/* \
    && JAVA_HOME_PATH="$(dirname "$(dirname "$(readlink -f "$(which javac)")")")" \
    && ln -s "$JAVA_HOME_PATH" /opt/java-home

# 3. Adım: Ortam Değişkenleri
# Debian Bookworm'da chromium /usr/bin altındadır.
ENV CHROME_BIN=/usr/bin/chromium \
    CHROME_DRIVER=/usr/bin/chromedriver \
    JAVA_HOME=/opt/java-home \
    MAVEN_OPTS=-Xmx1g \
    PATH=/opt/java-home/bin:$PATH

WORKDIR /app

COPY pom.xml .
COPY src ./src

# 4. Adım: Testi Başlat
ENTRYPOINT ["mvn", "test"]

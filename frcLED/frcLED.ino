#include "Ethernet.h"
#include "SPI.h"
#include "FastLED.h"

#define NUM_LEDS 150
CRGB leds[NUM_LEDS];
#define PIN 8

byte mac[] = {                                      //Creates a mac address for use in defining an Ethernet instance
  0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED
};

IPAddress ip(10, 8, 40, 6);
IPAddress robotIp(10, 8, 40, 2);

float progress = 1.0f;

EthernetClient robotClient;                         //Defines a client to be used to connect to the Robo Rio

String packet;
void showStrip() {
  // FastLED
  FastLED.show();
}

void setPixel(int Pixel, byte red, byte green, byte blue) {
  // FastLED
  leds[Pixel].r = red;
  leds[Pixel].g = green;
  leds[Pixel].b = blue;
}

void setAll(byte red, byte green, byte blue) {
  for (int i = 0; i < NUM_LEDS; i++) {
    setPixel(i, red, green, blue);
  }
  showStrip();
}

byte *getRainbowColor(byte colorWheelPos) {
  static byte color[3];

  if (colorWheelPos < 85) {
    color[0] = colorWheelPos * 3;
    color[1] = 255 - colorWheelPos * 3;
    color[2] = 0;
  } else if (colorWheelPos < 170) {
    colorWheelPos -= 85;
    color[0] = 255 - colorWheelPos * 3;
    color[1] = 0;
    color[2] = colorWheelPos * 3;
  } else {
    colorWheelPos -= 170;
    color[0] = 0;
    color[1] = colorWheelPos * 3;
    color[2] = 255 - colorWheelPos * 3;
  }

  return color;
}

int colorState = 0; // cycle through 255

void rainbowCycle() {
  byte *c;
  uint16_t i, j;
  int num_leds = (int) (progress * (float)(NUM_LEDS));
  Serial.println(num_leds);
  FastLED.clear();
  for (i = 0; i < num_leds; i++) {
    c = getRainbowColor(((i * 256 / num_leds) + colorState) & 255);
    setPixel(i, *c, *(c + 1), *(c + 2));
  }
  showStrip();
  colorState += 2;
  if (colorState > 255) {
    colorState = 0;
  }
}


void setup() {
  // put your setup code here, to run once:
  Ethernet.begin(mac, ip);
  Serial.begin(9600);
  FastLED.addLeds<WS2811, PIN, GRB>(leds, NUM_LEDS).setCorrection(TypicalLEDStrip);
}


void loop() {
  while (!robotClient.connected()) {
    robotClient.connect(robotIp, 5801);
    delay(3000);
  }
  // put your main code here, to run repeatedly:
  if (robotClient.available()) {
    packet = "";
    int i = 0;
    while (robotClient.available()) {
      char packetChar = (char) robotClient.read();
      if (i > 1) {
        packet += packetChar;
      }
      i++;
    }
    int newProgress = packet.toInt();
    progress = ((float) newProgress) / 100.0f;

    rainbowCycle();

    Serial.println(progress);
  }
  delay(10);
}

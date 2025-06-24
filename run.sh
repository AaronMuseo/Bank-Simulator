#!/bin/bash

# Set your JAR file name
JAR_FILE="dist/Bank.jar"

# Try common JavaFX installation paths
COMMON_PATHS=(
  "$HOME/javafx-sdk-21/lib"
  "$HOME/Downloads/javafx-sdk-21/lib"
  "/opt/javafx-sdk-21/lib"
  "/usr/lib/javafx-sdk-21/lib"
)

# Function to check if path exists
find_javafx_path() {
  for path in "${COMMON_PATHS[@]}"; do
    if [ -d "$path" ]; then
      echo "$path"
      return
    fi
  done
}

# Attempt to find JavaFX SDK
JAVAFX_PATH=$(find_javafx_path)

# If not found, prompt user
if [ -z "$JAVAFX_PATH" ]; then
  echo "⚠ JavaFX SDK not found in common locations."
  read -rp "👉 Please enter the path to JavaFX SDK 'lib' directory: " JAVAFX_PATH
  if [ ! -d "$JAVAFX_PATH" ]; then
    echo "❌ Invalid path. Exiting."
    exit 1
  fi
fi

# Run the JavaFX app
java --module-path "$JAVAFX_PATH" \
     --add-modules javafx.controls,javafx.fxml \
     -jar "$JAR_FILE"

#!/bin/bash
# filepath: build-frontend.sh

# Get the directory where this script is located
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Set directories relative to script location
FRONTEND_DIR="$SCRIPT_DIR/myplace-frontend"
BACKEND_STATIC_DIR="$SCRIPT_DIR/myplace/src/main/resources/META-INF/resources"

echo "Script directory: $SCRIPT_DIR"
echo "Frontend directory: $FRONTEND_DIR"
echo "Backend static directory: $BACKEND_STATIC_DIR"

# Build React app
echo "Building React app..."
cd "$FRONTEND_DIR" && npm run build

# Check if build was successful
if [ $? -ne 0 ]; then
  echo "React build failed!"
  exit 1
fi

# Create the static resources directory if it doesn't exist
mkdir -p "$BACKEND_STATIC_DIR"

# Remove old files
echo "Removing old static files..."
rm -rf "$BACKEND_STATIC_DIR"/*

# Copy build files to Quarkus static resources
echo "Copying build files to Quarkus static resources..."
cp -r "$FRONTEND_DIR/build"/* "$BACKEND_STATIC_DIR"/

echo "Done! React app has been built and copied to Quarkus static resources."
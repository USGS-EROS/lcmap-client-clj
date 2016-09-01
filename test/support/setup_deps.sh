#!/usr/bin/env bash

mkdir checkouts
cd checkouts && \
    git clone https://github.com/USGS-EROS/lcmap-config.git && \
    git clone https://github.com/USGS-EROS/lcmap-logger.git && \
    cd ../

mkdir ~/.usgs/
cp test/support/sample_config.ini ~/.usgs/lcmap.ini


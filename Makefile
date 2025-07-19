# Makefile

.PHONY: run clean build

run: clean build up

clean:
	docker compose down -v

build:
	docker compose build

up:
	docker compose up -d

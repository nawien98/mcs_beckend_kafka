.PHONY: docker-up
docker-up:
	docker-compose -f docker-compose.yaml up --build

.PHONY: docker-down
docker-down: ## Stop docker containers and clear artefacts.
	docker-compose -f docker-compose.yaml down
	docker system prune


.PHONY: run
run: packaging## run application
	./mvnw quarkus:dev

.PHONY: packaging
packaging: ## Packaging and run the application
	./mvnw clean package


.PHONY: clean
## clean: cleans temp
clean:
	@echo "Cleaning temp folder"
	@rm -rf temp/*
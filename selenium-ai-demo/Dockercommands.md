# Remove Target folder ->
rm -f ./selenium-ai-demo/target/test-classes/extent.properties && rm -f ./target/test-classes/extent.properties

# Triger build
cd selenium-ai-demo && docker compose up --build --abort-on-container-exit

# cucmber report
ls -l ./selenium-ai-demo/test-output/HtmlReport && ls -l ./selenium-ai-demo/test-output/CucumberReport

# Surfire report
ls -l ./selenium-ai-demo/test-output && ls -l ./selenium-ai-demo/target/surefire-reports

# Report mounting to copy the reports in host
docker ps -a
docker inspect selenium-ai-demo-test-runner-1 --format='{{.Mounts}}'
docker cp selenium-ai-demo-test-runner-1:/app/test-output ./selenium-ai-demo/
ls -l ./selenium-ai-demo/test-output && ls -l ./selenium-ai-demo/test-output/HtmlReport && ls -l ./selenium-ai-demo/test-output/CucumberReport
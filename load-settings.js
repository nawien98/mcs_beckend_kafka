var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost:27017/ms_accelerator_db');

var db = mongoose.connection;
 
db.on('error', console.error.bind(console, 'connection error:'));
 
db.once('open', function() {

  console.log("Connection Successful!");

  var SettingSchema = mongoose.Schema({
    name: String,
    values: [String],
    dependency: [String]
  });

  var Setting = mongoose.model('Setting', SettingSchema, 'settings');

  var setts =  [
    { name: 'language', values: ['Java','Kotlin','.NET'], dependency: ['']},
    { name: 'build', values: ['Maven','Gradle'], dependency: ['']},
    { name: 'framework', values: [ "Spring Boot", "Micronaut", "Quarkus"], dependency: ['Java']},
    { name: 'framework', values: [ "Helidon SE"], dependency: ['Java','Kotlin']},
    { name: 'framework', values: ["Ktor"], dependency: ['Kotlin']},
    { name: 'framework', values: [".NET Core"], dependency: ['.NET']},
    { name: 'deploy', values: ["Docker","Podman"], dependency: ['']},
    { name: 'orchestration', values: ["Kubernetes"], dependency: ['']},
    { name: 'security', values: ["Spring security"], dependency: ['Spring Boot']},
    { name: 'security', values: ["Micronaut security"], dependency: ['Micronaut']},
    { name: 'security', values: ["Quarkus security"], dependency: ['Quarkus']},
    { name: 'authentication', values: ["JWT","OAuth"], dependency: ['']},
    { name: 'tracing', values: ["OpenTracing"], dependency: ['']},
    { name: 'monitoring', values: ["Grafana"], dependency: ['']},
    { name: 'logging', values: ["Slf4j","Logstash"], dependency: ['']},
    { name: 'databases', values: ["Oracle", "MySQL", "MongoDB","SQLServer","Postgres"], dependency: ['']},
  ];

  Setting.collection.insertMany(setts, function (err, docs) {
    if (err){ 
        return console.error(err);
    } else {
      console.log("Multiple documents inserted to Collection");
    }
  });

});
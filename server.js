const express = require('express')
const mongoose = require('mongoose')
const app = express()

mongoose.connect('mongodb://localhost:27017/ms_accelerator_db');

var SettingSchema = mongoose.Schema({
  name: String,
  values: [String],
  dependency: [String]
});
app.use(express.urlencoded({extended:false}))
var Setting = mongoose.model('Setting', SettingSchema, 'settings');

app.set('view engine', 'ejs')
var finalsel;
app.get('/all', async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  const setg = await Setting.find();
  res.send(setg);
})

// return values without dependency
app.get('/primary', async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  const noDep = await Setting.find({dependency : ''});
  res.send(noDep);
})

// return values depending on given value  ans well as the null values
app.get('/depend', async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  let selected = req.query['name'];
  const depending = await Setting.find({ $or: [{ dependency : selected }, 
{ dependency: '' }] });
  res.send(depending);
})
// returns strictly only the value that has the current dependency

app.get('/nodepend', async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");
  let selected = req.query['name'];
  const depending = await Setting.find(
{ dependency: selected });
  res.send(depending);
})
//accepts array of names and sends back the values which are depending on the list as well as the values with no dependency
app.get('/dependList', async (req, res) => {
  res.header("Access-Control-Allow-Origin", "*");

  
  let selected =JSON.parse(req.query['dependencies'])
  //console.log(selected)
  // 
  const depending = await Setting.find( { $or: [ { dependency: { $in: selected } }, { dependency: '' } ] });
  res.send(depending);
  //console.log(depending)
})
app.post('/create_result',(req,res)=>{
  res.header("Access-Control-Allow-Origin", "*");
  //console.log(JSON.stringify(req.query['finalsel']))
  // console.log(req.body)
  let m=""
  for (let k in req.body){
    m=k;
  }
  finalsel=m;
  console.log(m)
  res.send(req.body)
  
})

app.listen(8080)

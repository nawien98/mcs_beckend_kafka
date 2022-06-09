import React,{useContext} from "react";
import {useNavigate } from "react-router-dom";
import './Dashboard.css'
import logo from './AccoliteLogo.png';
import { useAuth } from "./context/AuthContext";

import axios from 'axios';

  function Dashboard() {
    
 
//   //variable to hold the settings 
  
  
  
//   //variable for html generated form
//   const [form_out, setForm] = React.useState("");
//   // holds the overall list of features
//   const [finalsel, setFinalsel] = React.useState({ 
//    "language":'',
//    'build':'',
//    'deploy':'',
//    'framework':'',
//    'security':'',
//    'orchestration':'',
//    'authentication':'',
//    'tracing':'',
//    'monitoring':'',
//    'logging':'',
//    'databases':''
 
//   });
//   //set data with dependency
//   const [datadepend, setDatadepend] = React.useState("");
//   // holds the arraylist of all the elements which will intern determine the lists 
//   //which are going to be displayed
//   const [all_depend, setAll_depend] = React.useState([]);
//   //variable for user selection
//   const [selected,setSelected]=React.useState("")
//  // get the data of the selected element
//   const [data,setData]=React.useState("")
  
  
//   //function to update user selection
//   const updateSelection = (event) => {
//     //value1 will hold the type of feature eg :language ,framework ect
//     event.preventDefault()
//     const value1 = event.target.name
//     finalsel[value1]=event.target.id
//     setSelected(event.target.id)
//     //target if will help to determing thr value which is chosen
//     //the value will be then put to the respective dictionary
    
    

    
        
          
 
//   };
//   // API call to check if any of the values are present in the dependency
//   React.useEffect(()=>{
//     axios.get('http://localhost:8080/dependList?dependencies='+JSON.stringify(all_depend)).then((res)=>{
      
 
//       setDatadepend( res.data )
        
//     })

//   },[all_depend])
//   // API call to get the initial configrations settings and the dependent values

//   React.useEffect(()=>{

//     axios.get('http://localhost:8080/depend?name='+selected).then((res)=>{
//       setData( res.data )
//       let n=[]
      
//       res.data.map((e)=>{
       
//         n.push(e.name)
//         if(finalsel.hasOwnProperty(e.name) && finalsel[e.name]!==''){
//           if(!e.values.includes(finalsel[e.name])){
             
            
            
            
//                finalsel[e.name]=''
//           }
//         }
//       })
//       let arr=[]
     
//     // loop through the object and append the array with values which are not null(can be done better)
//      let c=[];
//            for (const key in finalsel){
//               // if the keys in the finals are not present in the key of the selected set then append the value
//              if(!n.includes(key) ){
//                c.push(key)
//              }
             
//              if (finalsel.hasOwnProperty(key) && finalsel[key]!=='' ) {

              
//               arr.push(finalsel[key])
               
//              }
//             }
            
          
//            //set the value of the overall array list of dependency
//             setAll_depend(arr)
//             // the difference between the no of keys in the selected items and the keys in the dictionary is 1 
//             //and that value is security then reselct the value (can be done better)
//             if(c.length===1 && c[0]==='security'){
//               finalsel['security']=''
//             }
//             console.log(finalsel)
//     })  
// },[selected])
  
  


//   React.useEffect(()  => {
//     if (datadepend !== null && datadepend !== "" && datadepend !== undefined){
      
//       //clean data (combine duplicates)
//       let dictionary={};
      
//       // will store the list of values so that they will not be repeated again
//       dictionary["values"]=[]
      

//       //nested map to navigate through the data and its inner values
//       setForm( datadepend.map((e) => 
//         { 
          
//           // if the name is aready present in the dictionary we should not repeate it again 
//           // example case of framework
//           if(e.name in dictionary  ){
          
          
          
//           return (<><span>
              
//             {
//                e.values.map((v) =>   
//                {
                 
//                   if(!(dictionary['values'].includes(v)))
//                   { 
//                     dictionary['values'].push(v)
//                     return (<span>
//                   <input type="radio" className="btn-check" name={e.name} id={v}  autoComplete="off"  onClick={updateSelection}  />
//                   <label className="btn btn-outline-secondary" htmlFor={v}>{v}</label>
//                 </span>)
                
//                 }
                
//               }
              
//              )
             
//               }
//           </span>
//           </>)

//         }
//         else{
          
          
//           dictionary[e.name]=<><br/><div>
//           {<h6>{e.name} :</h6>}
//           { e.values.map((v) =>          
//             {dictionary['values'].push(v)
//               return(<>
//               <input type="radio" className="btn-check" name={e.name} id={v} autoComplete="off" onClick={updateSelection} />
//               <label className="btn btn-outline-secondary" htmlFor={v}>{v}</label>
//             </>)}
            
//           )
          
//           }
//         </div>
        
//         </>
        
        
//           return (dictionary[e.name])
          
//         }
//           }
//       ));
//     }
//   }, [datadepend]);
//   function submit(e){
//     e.preventDefault();
//     let l=['language','build','deploy','framework']
    
//     let f=0
//     for (let i=0;i<l.length;i++){
//       if(finalsel[l[i]]===''){
//         f=1
//         break
//       }
//     }
  
//     if(!f){
      
//       axios.post('http://localhost:8080/create_result',JSON.stringify(finalsel))
//       .then(()=>{console.log("sent the data!")})
//       .catch(err=>{console.error(err)})

      
      
      
      
//     }

//   }
  






// return (
// 	<div
// 	style={{ 
// 		padding: "16px",
// 		margin: "16px",
// 	}}
// 	>
   
//    <form onSubmit={submit}>
    

//     {
      
//       form_out
//     }
  
//   <input type="submit" value="Submit" />
   
// 	</form>
  

// 	</div>
// );
};
export default Dashboard;
import * as React from "react";
import axios from 'axios';

/*
 LOGIC BROKEN:
  - doesn't hold previous selection 

*/

const Home = () => {


  //variable to hold the settings 
  const [data, setData] = React.useState("");
  //variable for user selection
  const [selected, setSelection] = React.useState("");
  //variable for html generated form
  const [form_out, setForm] = React.useState("");

  //function to update user selection
  const updateSelection = (event) => {
    setSelection(event.target.id);
  };

  // API call to get the initial configrations settings and the dependent values
  React.useEffect(() => {
    axios.get('http://localhost:8080/depend?name='+selected).then((res) => { setData( res.data ) });
  }, [selected]);


  React.useEffect(() => {
    if (data !== null && data !== "" && data !== undefined){
      
      //clean data (combine duplicates)


      //nested map to navigate through the data and its inner values
      setForm( data.map((e) => 
        <><div>
            {<h6>{e.name} :</h6>}
            { e.values.map((v) =>          
              <>
                <input type="radio" className="btn-check" name={e.name} id={v} autoComplete="off" onChange={updateSelection} />
                <label className="btn btn-outline-secondary" htmlFor={v}>{v}</label>
              </>
            )}
          </div><br/>
          </>
      ));
    }
  }, [data]);





return (
	<div
	style={{ 
		padding: "16px",
		margin: "16px",
	}}
	>
	<form>
    {
      form_out
    }
	</form>
	</div>
);
};

export default Home;
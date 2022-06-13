import axios, { AxiosResponse } from 'axios';

function getDependList(all_depend: string): Promise<AxiosResponse<any>> {
  return axios.get('http://localhost:8080/dependList?dependencies='+JSON.stringify(all_depend));
}

function getDepend(selected: string): Promise<AxiosResponse<any,any>> {
  return axios.get('http://localhost:8080/depend?name='+selected);
}

function createResult(finalsel: string): Promise<AxiosResponse<any,any>> {
  return axios.post('http://localhost:8080/create_result',JSON.stringify(finalsel));
}

export {getDependList,getDepend,createResult};


// export class HomeService {


//   // public getDependList(all_depend: string): any {
//   //   return axios.get('http://localhost:8080/dependList?dependencies='+JSON.stringify(all_depend));
//   // }

//   public getDepend(selected: string): any {
//   return axios.get('http://localhost:8080/depend?name='+selected);
//   }

//   public createResult(finalsel: string): any {
//     return axios.post('http://localhost:8080/create_result',JSON.stringify(finalsel));
//   }

// }



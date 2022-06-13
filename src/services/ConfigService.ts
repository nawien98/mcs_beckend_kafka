import axios, { AxiosResponse } from 'axios';

function getDependList(all_depend: any): Promise<AxiosResponse<any>> {
  return axios.get('http://localhost:8080/dependList?dependencies='+JSON.stringify(all_depend));
}

function getDepend(selected: string): Promise<AxiosResponse<any,any>> {
  return axios.get('http://localhost:8080/depend?name='+selected);
}

function createResult(finalsel: any): Promise<AxiosResponse<any,any>> {
  return axios.post('http://localhost:8080/create_result',JSON.stringify(finalsel));
}

export {getDependList,getDepend,createResult};


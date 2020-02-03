import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GenericService {

  private baseUrl = 'http://localhost:8082';
  private modals: any[] = [];

  constructor(private http: HttpClient) { }


  getGeneric(id: number): Observable<Object> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  createGeneric(url: any, object: Object): Observable<any> {
    return this.http.post(`${this.baseUrl}` + url, object);
  }

  createGenericList(url: any, any: Array<any>): Observable<any> {
    return this.http.post(`${this.baseUrl}` + url, any);
  }

  updateGeneric(url: any, id: number, object: Object): Observable<any> {
    return this.http.put(`${this.baseUrl}` + url + `/${id}`, object);
  }

  deleteGeneric(url: any, id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}` + url + `/${id}`);
  }

  getGenericPage(url: any, page: any, size: any): Observable<any> {
    return this.http.get(`${this.baseUrl}` + url + `?page=${page}&` + `size=${size}`);
  }
  getGenericByAuthPage(url: any, username: String, page: number, size: number): Observable<any> {
    return this.http.get(`${this.baseUrl}` + url + `/${username}` + `?page=${page}&` + `size=${size}`);
  }

  getGenericByAuthPageByTwoId(url: any, username: String, firstParturl: any, SecondPartUrl: any, firstId: number,
    secondId: number, page: any, size: any): Observable<any> {
    return this.http.get(`${this.baseUrl}` + url + `/${username}` +
      firstParturl + `/${firstId}` + SecondPartUrl + `/${secondId}` + `?page=${page}&` + `size=${size}`);
  }

  getGenericList(url: any): Observable<any> {
    return this.http.get(`${this.baseUrl}` + url);
  }

  getGenericPageByFilter(url: any, page: any, size: any, object: Object): Observable<any> {
    return this.http.post(`${this.baseUrl}` + url + `?page=${page}&` + `size=${size}`, object);
  }

  getGenericPageByFilterByAuthPage(url: any, username: String, page: any, size: any, object: Object): Observable<any> {
    return this.http.post(`${this.baseUrl}` + url + `/${username}` + `?page=${page}&` + `size=${size}`, object);
  }

  getGenericPagesById(firstParturl: any, SecondPartUrl: any, id: number, page, size): Observable<any> {
    return this.http.get(`${this.baseUrl}` + firstParturl + `/${id}` + SecondPartUrl + `?page=${page}&` + `size=${size}`);
  }

  getGenericById(url: any, id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}` + url + `/${id}`);
  }
  getGenericByBody(url: any,  object: Object): Observable<any> {
    return this.http.post(`${this.baseUrl}` + url , object);
  }

  deleteAll(): Observable<any> {
    return this.http.delete(`${this.baseUrl}` + `/delete`, { responseType: 'text' });
  }

  open(id: string) {
    // open modal specified by id
    const modal: any = this.modals.filter(x => x.id === id)[0];
    modal.open();
  }

  close(id: string) {
    // close modal specified by id
    const modal: any = this.modals.filter(x => x.id === id)[0];
    modal.close();
  }

  getUserByUsername(url: any, username: string): Observable<any> {
    return this.http.get(`${this.baseUrl}` + url + `/${username}`);
  }

  updateLoginUser(url: any, id: number, object: any): Observable<Object> {
    return this.http.put(`${this.baseUrl}` + url + `/${id}`, object);
  }

  getGenericByTwoId(firstParturl: any, SecondPartUrl: any, firstIid: number, secondId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}` + firstParturl + `/${firstIid}` + SecondPartUrl + `/${secondId}`);
  }

  getGenericByTwoIdByPage(firstParturl: any, SecondPartUrl: any, firstId: number, secondId: number, page: any, size: any): Observable<any> {
    return this.http.get(`${this.baseUrl}` + firstParturl + `/${firstId}` + SecondPartUrl + `/${secondId}` +
      `?page=${page}&` + `size=${size}`);
  }

  updateStateUserAccount(url1: any, id: number, url2: any, body: any): Observable<any> {
    return this.http.put(`${this.baseUrl}` + url1 + `/${id}` + url2, body);
  }
  updateShared(url1: any, id: number, url2: any, body: any): Observable<any> {
    return this.http.put(`${this.baseUrl}` + url1 + `/${id}` + url2, body);
  }

  deleteWithBody(url: any, body: any): Observable<any> {
    return this.http.delete(`${this.baseUrl}` + url, body);
  }

  searchGenericByAuthPage(url: any, motCle: String, username: String, page: any, size: any): Observable<any> {
    return this.http.get(`${this.baseUrl}` + url + `/${username}` + `/${motCle}` + `?page=${page}&` + `size=${size}`);
  }

  searchGenericByAuthPageByTwoId(url: any, term: String, username: String, firstId: number, secondId: number,
    page: any, size: any): Observable<any> {
    return this.http.get(`${this.baseUrl}` + url + `/${username}` + `/${term}` + `/${firstId}` + `/${secondId}` +
      `?page=${page}&` + `size=${size}`);
  }

  searchGeneric(url: any, motCle: String, page: any, size: any): Observable<any> {
    return this.http.get(`${this.baseUrl}` + url + `/${motCle}` + `?page=${page}&` + `size=${size}`);
  }

  deleteListOfMessages(url: any, any: Array<any>): Observable<any> {
    return this.http.post(`${this.baseUrl}` + url, any);
  }

}

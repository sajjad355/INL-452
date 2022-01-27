
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
// import { AppComponent } from '../../app.component';
import { UrlService } from 'app/shared/services/url.service';
import { Observable } from 'rxjs';
import { item, ItemWDetails } from '../objects/item';
import { SearchUtils } from '../util/SearchUtil';


@Injectable({
    providedIn: 'root',
})
export class InventoryService {
    url: string = UrlService.URL + `/invent/`;
    invent: item[];

    constructor(private http: HttpClient) { }

    public search(searchKey: string): Observable<item[]> {

        let st : string = SearchUtils.ReplaceSearchTerms( searchKey );
        return this.http.get<item[]>(this.url + `search/${st}`);
    }

    public searchSingleInventoryByCat(cat: string): Observable<item> {

        return this.http.get<item>(this.url + `searchSingleInventoryByCat/${cat}`);
    }

    public searchSingleInventoryByName(name: string): Observable<item> {

        name = name.replace(/\//g, 'slash');
        name = name.replace(/\s/g, 'whitespace');
        name = name.replace(/#/g, 'pong');
        name = name.replace(/%/g, 'percent');
        name = name.replace(/\?/g, 'questionmark');
        return this.http.get<item>(this.url + `searchSingleInventoryByName/${name}/`);
    }

    // public loadInventory(): Observable<item[]> {
    //
    //     return this.http.get<item[]>(this.url + 'all/');
    // }

    public loadInventoryWDetails(page: number): Observable<ItemWDetails[]> {

        return this.http.get<ItemWDetails[]>(this.url + 'allWDetails/' + page);
    }

    public searchInventory(search: string, page: number, searchArea:string, sublocation?:string): Observable<ItemWDetails[]> {

        //encode all white space and #
        search = search.replace(/\//g, 'slash');
        search = search.replace(/\s/g, 'whitespace');
        search = search.replace(/#/g, 'pong');
        search = search.replace(/%/g, 'percent');
        search = search.replace(/\?/g, 'questionmark');
        if(searchArea == undefined || searchArea == '' || searchArea == 'All'){
            return this.http.get<ItemWDetails[]>(this.url + 'searchWPage/' + search + '/' + page);
        }
        if(searchArea == 'cat'){
            return this.http.get<ItemWDetails[]>(this.url + 'searchByCatWPage/' + search + '/' + page);
        }
        if(searchArea == 'name'){
            return this.http.get<ItemWDetails[]>(this.url + 'searchByNameWPage/' + search + '/' + page);
        }
        if(searchArea == 'location'){
            return this.http.get<ItemWDetails[]>(this.url + 'searchByLocWPage/' + search + '/' + page);
        }
        if(searchArea == 'sublocation'){
            return this.http.get<ItemWDetails[]>(this.url + 'searchBySubLocWPage/' + search + '/' + page);
        }
        if(searchArea == 'suppliermanufacturer'){
            return this.http.get<ItemWDetails[]>(this.url + 'searchBySupplierorManufacturerWPage/' + search + '/' + page);
        }
        if(searchArea == 'itemtype'){
            return this.http.get<ItemWDetails[]>(this.url + 'searchByItemTypeWPage/' + search + '/' + page);
        }
        if(searchArea == 'locationandsublocation'){
            if(sublocation){
                sublocation = sublocation.replace(/\//g, 'slash');
                sublocation = sublocation.replace(/\s/g, 'whitespace');
                sublocation = sublocation.replace(/#/g, 'pong');
                sublocation = sublocation.replace(/%/g, 'percent');
                sublocation = sublocation.replace(/\?/g, 'questionmark');
                return this.http.get<ItemWDetails[]>(this.url + 'searchByLocationAndSubLocWPage/' + search + '/'+ sublocation + '/' + page);
            }

        }
    }


    public getSearchCount(search: string,searchArea:string, sublocation?:string): Observable<number> {
        //encode all white space and #

        search = search.replace(/\//g, 'slash');
        search = search.replace(/\s/g, 'whitespace');
        search = search.replace(/#/g, 'pong');
        search = search.replace(/%/g, 'percent');
        search = search.replace(/\?/g, 'questionmark');
        if(searchArea == undefined || searchArea == '' || searchArea == 'All'){
            return this.http.get<number>(this.url + 'searchAllCount/' + search + '/');
        }
        if(searchArea == 'cat'){
            return this.http.get<number>(this.url + 'searchByCatCount/' + search + '/');
        }
        if(searchArea == 'name'){
            return this.http.get<number>(this.url + 'searchByNameCount/' + search + '/');
        }
        if(searchArea == 'location'){
            return this.http.get<number>(this.url + 'searchByLocatonCount/' + search + '/');
        }
        if(searchArea == 'sublocation'){
            return this.http.get<number>(this.url + 'searchBySubLocatonCount/' + search + '/');
        }
        if(searchArea == 'suppliermanufacturer'){
            return this.http.get<number>(this.url + 'searchBySupplierorManufacturerCount/' + search + '/');
        }
        if(searchArea == 'itemtype'){
            return this.http.get<number>(this.url + 'searchByItemtypeCount/' + search + '/');
        }
        if(searchArea == 'locationandsublocation'){
            if(sublocation){
                sublocation = sublocation.replace(/\//g, 'slash');
                sublocation = sublocation.replace(/\s/g, 'whitespace');
                sublocation = sublocation.replace(/#/g, 'pong');
                sublocation = sublocation.replace(/%/g, 'percent');
                sublocation = sublocation.replace(/\?/g, 'questionmark');
                return this.http.get<number>(this.url + 'searchByLocationAndSubLocationCount/' + search + '/'+ sublocation + '/');
            }

        }
    }


    public getCount(): Observable<number> {

        return this.http.get<number>(this.url + 'count/');
    }

    public getItemWithId(id: number): Observable<item> {

        return this.http.get<item>(this.url + 'get/' + id + '/');
    }



    public addItem(i: item): Promise<Object> {
        if (i == undefined) return;

        return this.http.post(this.url + 'add/', JSON.stringify(i)).toPromise();
    }

    public updateItem(i: item): Promise<Object> {
        if (i == undefined) return;

        return this.http.put(this.url + 'update/' + (i.dbid) + '/', JSON.stringify(i)).toPromise();
    }

    public deleteItem(i: item): Promise<Object> {

        if (i == undefined) return;
        return this.http.delete(this.url + 'delete/' + (i.dbid) + '/' + i.modifyperson + '/').toPromise();
    }
}

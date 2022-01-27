export class StringUtils {

  // Checks that the field is defined and is not equal to any of the passed terms - i.e. ' ', '-' 
  static IsDefinedAndNotEqualsTerms(field: string, ...terms: any[]) : boolean {  
    if ( !field ) return false;
    if ( terms && terms.includes( field )) return false;
    return true;
  }  
}
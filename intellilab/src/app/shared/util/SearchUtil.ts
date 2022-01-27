export class SearchUtils {

  // encode all white space and #
  static ReplaceSearchTerms(searchTerm: string) {
    let st: string = searchTerm;
    if ( !st) st = '';
    st = st.replace(/^\s+/,"");
    st = st.replace(/\//g, 'slash');
    st = st.replace(/\s/g, 'whitespace');
    st = st.replace(/#/g, 'pong');
    st = st.replace(/%/g, 'percent');
    st = st.replace(/\?/g, 'questionmark');
    st = st.replace(/\./g, 'period');
    return st;
  }
}

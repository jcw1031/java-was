package codesquad.http.parser;

import codesquad.format.Parser;
import codesquad.http.QueryParameters;

import java.util.Arrays;

public class QueryParametersParser implements Parser<QueryParameters> {

    @Override
    public QueryParameters parse(String uriText) {
        if (uriText == null || uriText.isEmpty()) {
            return QueryParameters.empty();
        }

        int queryIndex = uriText.indexOf("?");
        if (queryIndex == -1) {
            return QueryParameters.empty();
        }
        String queryString = uriText.substring(queryIndex + 1);

        QueryParameters queryParameters = new QueryParameters();
        Arrays.stream(queryString.split("&"))
                .forEach(pair -> putValue(pair, queryParameters));
        return queryParameters;
    }

    private static void putValue(String pair, QueryParameters queryParameters) {
        String[] keyValue = pair.split("=", 2);
        if (keyValue.length == 2) {
            String key = keyValue[0];
            String value = keyValue[1];
            queryParameters.addValue(key, value);
        } else {
            queryParameters.addValue(keyValue[0], "");
        }
    }

}

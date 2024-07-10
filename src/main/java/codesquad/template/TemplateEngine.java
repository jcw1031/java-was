package codesquad.template;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateEngine {

    private static final Pattern TAG_PATTERN = Pattern.compile("<([^>]+)>(.*?)</[^>]+>|<[^>]+>", Pattern.DOTALL);
    private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile("(jcw:[^=]+)=\"([^\"]+)\"");

    public static String processTemplate(String html, Map<String, String> context) {
        StringBuilder result = new StringBuilder();
        Matcher tagMatcher = TAG_PATTERN.matcher(html);

        while (tagMatcher.find()) {
            String fullTag = tagMatcher.group(0);
            String tagContent = tagMatcher.group(2);
            String processedTag = processTag(fullTag, tagContent, context);
            tagMatcher.appendReplacement(result, Matcher.quoteReplacement(processedTag));
        }
        tagMatcher.appendTail(result);

        return result.toString();
    }

    private static String processTag(String fullTag, String tagContent, Map<String, String> context) {
        Matcher attributeMatcher = ATTRIBUTE_PATTERN.matcher(fullTag);
        StringBuilder processedTag = new StringBuilder();

        boolean textReplaced = false;
        String textReplacement = null;
        while (attributeMatcher.find()) {
            String attribute = attributeMatcher.group(1);
            String value = attributeMatcher.group(2);

            if ("jcw:text".equals(attribute)) {
                textReplacement = getContextValue(value, context);
                textReplaced = true;
                attributeMatcher.appendReplacement(processedTag, "");
            } else if ("jcw:href".equals(attribute)) {
                String replacement = getContextValue(value, context);
                attributeMatcher.appendReplacement(processedTag, "href=\"" + Matcher.quoteReplacement(replacement) + "\"");
            }
        }
        attributeMatcher.appendTail(processedTag);

        String cleanedTag = processedTag.toString().replaceAll("jcw:[^=]+=\"[^\"]+\"\\s*", "");

        if (textReplaced) {
            int closeTagIndex = cleanedTag.indexOf(">");
            if (closeTagIndex != -1) {
                String openingTag = cleanedTag.substring(0, closeTagIndex + 1);
                String closingTag = cleanedTag.substring(cleanedTag.lastIndexOf("<"));
                return openingTag + textReplacement + closingTag;
            }
        }
        return cleanedTag;
    }

    private static String getContextValue(String key, Map<String, String> context) {
        if (key.startsWith("${") && key.endsWith("}")) {
            key = key.substring(2, key.length() - 1);
        }
        return context.getOrDefault(key, "");
    }

    public static void main(String[] args) {
        String html = "<a jcw:text=\"${nickname}\" jcw:href=\"${url}\">Default Text</a>";
        Map<String, String> context = new HashMap<>();
        context.put("nickname", "지찬우");
        context.put("url", "/main");

        String processed = processTemplate(html, context);
        System.out.println(processed);
    }
}
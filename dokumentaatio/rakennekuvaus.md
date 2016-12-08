## Rakennekuvaus

Main luo EditorPagen, joka on ohjelman ainoa "sivu"/Page olio. EditorPage luo UI kontrollit, eli toolbarin, CodeEditorControllin sekä Consolen.

EditorPage myös luo EditorViewModelin, joka omistaa CodeSnippetViewModelin. Tulevaisuudessa ohjelmaan voi implementoida sen, että voi olla monta eri tiedostoa samaan aikaan auki, jolloin EditorViewModelissa olisi lista avoinna olevista CodeSnippetViewModeleista. CodeSnippetVM omistaa CodeSnippetin, ja CodeSnippet.getLanguage():n, eli snippetin koodauskielen, perusteella luo CodeFormatterin, CodeParserin sekä CodeCompilerin.

CodeFormatteri formatoi koodia (auto-indent tällä hetkellä vain). CodeParser muuttaa koodin "blokeiksi", esimerkiksi "Comment", "StringLiteral", "Keyword" jne. Tätä käytetään esim syntax highlightaukseen. CodeCompiler kääntää koodin ja sen avulla voi myös ajaa käännetyn koodin.
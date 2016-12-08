## Rakennekuvaus

Main luo EditorPagen, joka on ohjelman ainoa "sivu"/Page olio. EditorPage luo UI kontrollit, eli toolbarin, CodeEditorControllin sek� Consolen.

EditorPage my�s luo EditorViewModelin, joka omistaa CodeSnippetViewModelin. Tulevaisuudessa ohjelmaan voi implementoida sen, ett� voi olla monta eri tiedostoa samaan aikaan auki, jolloin EditorViewModelissa olisi lista avoinna olevista CodeSnippetViewModeleista. CodeSnippetVM omistaa CodeSnippetin, ja CodeSnippet.getLanguage():n, eli snippetin koodauskielen, perusteella luo CodeFormatterin, CodeParserin sek� CodeCompilerin.

CodeFormatteri formatoi koodia (auto-indent t�ll� hetkell� vain). CodeParser muuttaa koodin "blokeiksi", esimerkiksi "Comment", "StringLiteral", "Keyword" jne. T�t� k�ytet��n esim syntax highlightaukseen. CodeCompiler k��nt�� koodin ja sen avulla voi my�s ajaa k��nnetyn koodin.
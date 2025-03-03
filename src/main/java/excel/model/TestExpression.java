//package excel.model;
//
//public class TestExpression {
//    public static void main(String[] args) {
//        String s = "= 7+8-3/22";
//        ExpressionBuilder builder = new ExpressionBuilder();
//        Expression exp = builder.build(s);
//
//        double result = exp.interpret();
//        System.out.println("Résultat: " + formatResult(result));
//    }
//
//    /**
//     * Formate le résultat pour l'affichage :
//     * - Supprime les décimales inutiles si c'est un entier.
//     * - Sinon, affiche deux chiffres après la virgule.
//     * - La meme fonction est dans SpreadsheetCellModel.java → SetResult() pour la cellule.
//     *
//     * @param result Le nombre à formater.
//     * @return Une chaîne représentant le résultat formaté.
//     */
//    private static String formatResult(double result) {
//        if (result % 1 == 0) {
//            return String.valueOf((int) result); // Affiche sans décimales si c'est un entier
//        } else {
//            return String.format("%.2f", result).replace(",", "."); // Sinon, garde 2 chiffres après la virgule
//        }
//    }
//}

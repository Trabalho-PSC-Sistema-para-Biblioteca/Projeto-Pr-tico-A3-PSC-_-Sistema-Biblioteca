/**
 * Esta classe representa o usuário atualmente logado no sistema.
 * Os dados desta classe são obtidos de uma instância da classe Funcionario.
 * O código-fonte foi descompilado de um arquivo .class usando o decompilador FernFlower.
 * 
 * @author [Seu Nome]
 * @version 1.0
 * @since 2024-06-16
 */// Source code is decompiled from a .class file using FernFlower decompiler.
package model;

/**
 * 
 *@author tayna
 */

public class UsuarioLogado {
   private static Funcionario funcionario;
   /**
    * Define o funcionário atualmente logado no sistema.
    * 
    * @param func O funcionário a ser definido como logado.
    */

   public UsuarioLogado() {
   }

   public static void setFuncionario(Funcionario func) {
      funcionario = func;
   }
   /**
    * Obtém o funcionário atualmente logado no sistema.
    * 
    * @return O funcionário atualmente logado no sistema.
    */

   public static Funcionario getFuncionario() {
      return funcionario;
   }
}

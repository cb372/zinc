package xsbt

import scala.tools.nsc.Global

trait GlobalHelpers {
  val global: Global
  import global._

  def symbolsInType(tp: Type): Set[Symbol] = {
    val typeSymbolCollector =
      new CollectTypeCollector({
        case tpe if (tpe != null) && !tpe.typeSymbolDirect.hasPackageFlag => tpe.typeSymbolDirect
      })

    typeSymbolCollector.collect(tp).toSet
  }

  object MacroExpansionOf {
    def unapply(tree: Tree): Option[Tree] = {
      tree.attachments.all.collect {
        case att: analyzer.MacroExpansionAttachment => att.expandee
      }.headOption
    }
  }
}

package thehighman.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;

public class SacoDeLanchesSemFundo extends AbstractRelic {
    public static final String ID = "thehighman:SacoDeLanchesSemFundo";

    public SacoDeLanchesSemFundo() {
        super(ID, "sacolanches.png", RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return "Sempre que você consumir um stack de Comido, compre 1 carta.";
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SacoDeLanchesSemFundo();
    }

    // Este método deve ser chamado pela lógica que consome Comido
    public void onComidoConsumido() {
        flash();
        addToBot(new DrawCardAction(AbstractDungeon.player, 1));
    }
}
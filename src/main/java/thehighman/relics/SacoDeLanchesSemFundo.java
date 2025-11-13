package thehighman.relics;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
@AutoAdd.Seen
public class SacoDeLanchesSemFundo extends BaseRelic {
    public static final String ID = "highman:SacoDeLanchesSemFundo";

    public SacoDeLanchesSemFundo() {
        super(ID,  RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SacoDeLanchesSemFundo();
    }

    // Este método deve ser chamado pela lógica que consome Comido
    public void onComidoConsumido(AbstractPlayer p) {
        addToBot(new DrawCardAction(p, 1));
        flash();
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
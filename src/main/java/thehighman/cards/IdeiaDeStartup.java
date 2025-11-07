package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.Erva;
import thehighman.util.CardStats;

public class IdeiaDeStartup extends BaseCard {
    public static final String ID = makeID("IdeiaDeStartup");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            2
    );

    private static final int ERVA_GAIN = 5;

    public IdeiaDeStartup() {
        super(ID, info);
        setMagic(ERVA_GAIN);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Ganha 5 de Erva
        addToBot(new ApplyPowerAction(p, p, new Erva(p, p, this.magicNumber), this.magicNumber));

        // Zera a energia restante
        AbstractDungeon.player.energy.use(AbstractDungeon.player.energy.energy);
    }
}
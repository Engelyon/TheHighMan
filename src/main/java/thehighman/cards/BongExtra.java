package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.BongExtraPower;
import thehighman.util.CardStats;

public class BongExtra extends BaseCard {
    public static final String ID = makeID("BongExtra");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK_PER_TRIGGER = 5;
    private static final int BLOCK_PER_TRIGGER_UPG = 8;

    public BongExtra() {
        super(ID, info);
        this.keywords.add("bong");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int blockPerTrigger = upgraded ? BLOCK_PER_TRIGGER_UPG : BLOCK_PER_TRIGGER;
        addToBot(new ApplyPowerAction(p, p, new BongExtraPower(p, p, blockPerTrigger), blockPerTrigger));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName(); // ajustar custo aqui se quiser
            initializeDescription();
        }
    }
}
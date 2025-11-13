package thehighman.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import thehighman.character.TheHighman;
import thehighman.powers.ErvaPower;
import thehighman.powers.SedaPower;
import thehighman.util.CardStats;

public class MaoSanta extends BaseCard {
    public static final String ID = makeID("MaoSanta");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int BUFF_AMOUNT = 1;

    private static final int UPG_BUFF = 1;

    public MaoSanta() {
        super(ID, info);
        setMagic(BUFF_AMOUNT, UPG_BUFF);
        this.keywords.add("seda");
        this.keywords.add("erva");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new InflameEffect(p)));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new SedaPower(p, p, this.magicNumber), this.magicNumber)
        );
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new ErvaPower(p, this.magicNumber), this.magicNumber)
        );
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_BUFF);
            initializeDescription();
        }
    }
}
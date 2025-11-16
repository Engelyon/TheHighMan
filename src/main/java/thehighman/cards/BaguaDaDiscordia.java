package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.util.CardStats;

public class BaguaDaDiscordia extends BaseCard {
    public static final String ID = makeID("BaguaDaDiscordia");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            1
    );

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 6;

    public BaguaDaDiscordia() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.isMultiDamage = true;
        this.exhaust=true;
        this.cardsToPreview = new BadTrip();
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL,
                com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        for (int i = 0; i < 2; i++) {
            AbstractCard badTrip = new BadTrip();
            badTrip.modifyCostForCombat(0);
            AbstractDungeon.player.drawPile.addToRandomSpot(badTrip);
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            initializeDescription();
        }
    }
}
package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.actions.DrawCheckBadTripAction;
import thehighman.util.CardStats;

public class LoopMental extends BaseCard {
    public static final String ID = makeID("LoopMental");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 10;
    private static final int DRAW = 2;// up: compra +3

    public LoopMental() {
        super(ID, info);
        setDamage(DAMAGE,3);
        setMagic(DRAW,1);
        this.keywords.add("bad trip");
        this.cardsToPreview = new BadTrip();
        initializeDescription();
    }

   /* @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new DrawCardAction(p, magicNumber));
    }*/

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        // snapshot da mão antes do draw
        final java.util.Set<String> before = new java.util.HashSet<>();
        if (p != null && p.hand != null) {
            for (com.megacrit.cardcrawl.cards.AbstractCard c : p.hand.group) {
                before.add(c.cardID + "|" + c.uuid);
            }
        }

        // enfileira o draw inicial
        addToBot(new DrawCardAction(p, this.magicNumber));

        // ação que roda depois do draw para checar se alguma BadTrip foi desenhada
        addToBot(new com.megacrit.cardcrawl.actions.AbstractGameAction() {
            @Override
            public void update() {
                boolean foundBadTrip = false;
                if (p != null && p.hand != null) {
                    for (com.megacrit.cardcrawl.cards.AbstractCard c : p.hand.group) {
                        String key = c.cardID + "|" + c.uuid;
                        if (!before.contains(key)) {
                            // aqui detectamos as cartas recém desenhadas; verifique o ID de BadTrip
                            // substitua "BadTrip" pelo ID real da sua carta se necessário (ex: BadTrip.ID)
                            if (c.cardID.equals("BadTrip")) {
                                foundBadTrip = true;
                                break;
                            }
                        }
                    }
                }

                if (foundBadTrip && magicNumber > 0) {
                    AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, magicNumber));
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }
}